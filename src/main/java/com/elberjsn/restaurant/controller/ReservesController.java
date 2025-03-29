package com.elberjsn.restaurant.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elberjsn.restaurant.models.Board;
import com.elberjsn.restaurant.models.Client;
import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.models.Restaurant;
import com.elberjsn.restaurant.service.BoardService;
import com.elberjsn.restaurant.service.ClientService;
import com.elberjsn.restaurant.service.ReserveService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/my/reserve")
public class ReservesController {
    @Autowired
    ReserveService reserveService;

    @Autowired
    BoardService boardService;

    @Autowired
    ClientService clientService;

    @GetMapping("/")
    public String getMethodName(Model model) {
        return "infos/reserves";
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveReserve(@RequestParam("dateNewReserve") String dateNewReserve,
            @RequestParam("timeNewReserve") String timeNewReserve,
            @RequestParam("tel") String tel, @RequestParam("idClient") String idClient,
            @RequestParam("nameClient") String nameClient, @RequestParam("emailClient") String emailClient,
            @RequestParam("obs") String obs, @RequestParam("selectBoard") String selectBoard,
            @RequestParam("qtd") String qtd, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");

        LocalTime timeStart = LocalTime.parse(timeNewReserve);
        Board board = boardService.boardByNumber(Integer.valueOf(selectBoard), id);
        Client client = new Client();
        client = clientService.clientByPhone(tel);

        if (client.getId() == null) {

            client.setEmail(emailClient);
            client.setName(nameClient);
            client.setPhone(tel);
            client = clientService.save(client);
        }

        Reserve newReserve = new Reserve();
        newReserve.setDtReserve(LocalDate.parse(dateNewReserve));
        newReserve.setHoursStart(timeStart);
        newReserve.setHoursEnd(timeStart.plusMinutes(90));
        newReserve.setStatus("Reservado");
        newReserve.setObs(obs);
        newReserve.setQuantity(Integer.valueOf(qtd));
        newReserve.setBoard(board);
        newReserve.setRestaurant(board.getRestaurant());
        newReserve.setClient(client);
        Reserve r = reserveService.save(newReserve);
        if (r.getId() != null) {
            return ResponseEntity.ok("Reserva Cria Com Sucesso");
        } else {
            return ResponseEntity.badRequest().body("Não Foi Possivel criar a Reserva");
        }

    }

    @PostMapping("/active")
    public ResponseEntity<String> reservesActiveToday(HttpServletRequest request) {
        LocalDate today = LocalDate.now();
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");

        String reservesActive = String.valueOf(reserveService.findByReservesToday(today, id).stream().count());

        return ResponseEntity.ok(reservesActive);
    }

    @PostMapping("/availableBoadsByDate")
    public ResponseEntity<String> boardsAvailableByDate(@RequestParam("dateNewReserve") String dateNewReserve,
            @RequestParam("timeNewReserve") String timeNewReserve, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");

        LocalDate dataFind = LocalDate.parse(dateNewReserve);
        LocalTime timeFind = LocalTime.parse(timeNewReserve);

        Map<Integer, Integer> board = reserveService.reserveBetweenDate(dataFind, timeFind, id);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(board);
            return ResponseEntity.ok(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Sem Mesas Disponiveis!");
    }

    @GetMapping("/weekChart")
    public ResponseEntity<String> weekChart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");
        LocalDate week = LocalDate.now();
        List<Reserve> reserves = reserveService.reserveWeek(week, id);
        Map<String, Integer> charData = new HashMap<>() {
            {
                put(week.getDayOfWeek().toString(), 0);
                put(week.plusDays(1).getDayOfWeek().toString(), 0);
                put(week.plusDays(2).getDayOfWeek().toString(), 0);
                put(week.plusDays(3).getDayOfWeek().toString(), 0);
                put(week.plusDays(4).getDayOfWeek().toString(), 0);
                put(week.plusDays(5).getDayOfWeek().toString(), 0);
                put(week.plusDays(6).getDayOfWeek().toString(), 0);
            }
        };
        for (Reserve reserve : reserves) {
            if (reserve.getDtReserve().equals(week)) {
                charData.replace(week.getDayOfWeek().toString(), charData.get(week.getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(1))) {
                charData.replace(week.plusDays(1).getDayOfWeek().toString(),
                        charData.get(week.plusDays(1).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(2))) {
                charData.replace(week.plusDays(2).getDayOfWeek().toString(),
                        charData.get(week.plusDays(2).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(3))) {
                charData.replace(week.plusDays(3).getDayOfWeek().toString(),
                        charData.get(week.plusDays(3).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(4))) {
                charData.replace(week.plusDays(4).getDayOfWeek().toString(),
                        charData.get(week.plusDays(4).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(5))) {
                charData.replace(week.plusDays(5).getDayOfWeek().toString(),
                        charData.get(week.plusDays(5).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(6))) {
                charData.replace(week.plusDays(6).getDayOfWeek().toString(),
                        charData.get(week.plusDays(6).getDayOfWeek().toString()) + 1);
            }

        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(charData);
            return ResponseEntity.ok(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("{}");
    }

    @PostMapping("/init")
    public ResponseEntity<String> initReserve(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");
        LocalDate today = LocalDate.now();

        var resposta = reserveService.boardsReservesToday(today, id);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        for (String t : resposta) {

            String[] strArray = t.split(",");

            ObjectNode childNode1 = mapper.createObjectNode();
            childNode1.put("id", strArray[0]);
            childNode1.put("board", strArray[1]);
            childNode1.put("name", strArray[2]);
            childNode1.put("hoursStart", strArray[3]);

            rootNode.set("" + strArray[0], childNode1);
        }
        String jsonString = "";
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            return ResponseEntity.ok(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(jsonString);
    }

   

}
