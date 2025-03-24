package com.elberjsn.restaurant.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elberjsn.restaurant.DTO.BoardDTO;
import com.elberjsn.restaurant.models.Board;
import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.models.Restaurant;
import com.elberjsn.restaurant.security.JwtUtil;
import com.elberjsn.restaurant.service.BoardService;
import com.elberjsn.restaurant.service.ControlService;
import com.elberjsn.restaurant.service.ReserveService;
import com.elberjsn.restaurant.service.RestaurantService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/my")
public class DashBoardController {

    Restaurant restaurant = new Restaurant();
    List<?> clientsToday = new ArrayList<>();
    int boardDisposable = 0; // falta
    double balanceToday = 0;
    int reservesToday = 0;
    int clientToday = 0;
    int boardsFreeToday = 0;
    String cnpj = null;
    Long idRestaurant=null;
    int allBoards=0;
    ArrayList<BoardDTO> allBoardEntity = new ArrayList<>();

    @Autowired
    HttpSession httpSession;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    ReserveService reserveService;

    @Autowired
    ControlService controlService;

    @Autowired
    BoardService boardService;

    
    
    @GetMapping("/dash")
    public String dashBoard(Model model,RedirectAttributes attributes) {

        DateTimeFormatter formData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        initDash(cnpj);
        model.addAttribute("restaurant", this.restaurant);
        if (this.restaurant.getName() == null) {
            model.addAttribute("restaurant", this.restaurant);
            attributes.addFlashAttribute("msg","Por favor Termine seu cadastro!");

            return "redirect:/my/config";
        }

        
        model.addAttribute("reservesToday", this.reservesToday);
        model.addAttribute("clientToday", this.clientToday);
        model.addAttribute("clientsToday", this.clientsToday);
        model.addAttribute("balanceToday", this.balanceToday);
        model.addAttribute("boardsFreeToday", this.boardsFreeToday);
        model.addAttribute("today", LocalDate.now().format(formData));

        return "infos/dashboard";
    }
    public String erroToken(Model model,RedirectAttributes attributes){
        
        attributes.addFlashAttribute("msg", "Informações de Login invalida!");
           
        return "redirect:/login";

    }
    @GetMapping("/")
    public String checkTokenDash(HttpServletRequest request,Model model,@RequestParam("token") String token,RedirectAttributes attributes,HttpSession httpSession) {
            String tokenHeader = request.getHeader("Authorization");
            if (tokenHeader == null) {
                tokenHeader = token;
            }

            if (checkToken(tokenHeader)) {
                httpSession.setAttribute("token", tokenHeader);
                return dashBoard(model,attributes);
            } else {
                return erroToken(model,attributes);
            }

            
    }

    public boolean checkToken(String token){
        if (JwtUtil.validarToken(token) != null && !JwtUtil.isTokenExpirado(token)) {
            return true;// Redirecionar para dashboard se o token for válido
        } else {
            return false;
        }
    }

    public Map<String,?> initPage(Model model){

        Map<String,Object> attrs = new HashMap<>();
        String token = (String) httpSession.getAttribute("token");

        attrs.put("restaurant", this.restaurant);
        attrs.put("idRest", this.idRestaurant);
        attrs.put("token", token);

        return attrs;
    }   

    @PostMapping("/board/new")
    public String newBoard(@ModelAttribute Board board,Model model) {
        Restaurant r = restaurantService.restaurantByCnpj(this.cnpj);

        
        board.setRestaurant(r);
        boardService.saveBoard(board);

        model.addAllAttributes(initPage(model));
        return "redirect:/my/boards";
    }


    @GetMapping("/reserves")
    public String reserves(Model model) {
        model.addAllAttributes(initPage(model));
        return "infos/reserves";
    }
    public Long findIdRestaurant(){
        String token = (String) httpSession.getAttribute("token");
        if (token==null) {
            
        }
        return  Long.parseLong(JwtUtil.decoderToken(token));
    }

    @GetMapping("/boards")
    public String boards(Model model) {
        
        Board b = new Board();
        
        
        model.addAttribute("boardsQtd",this.allBoards);
        model.addAttribute("boardsEntity",this.allBoardEntity);
        model.addAttribute("freeBoards",this.boardsFreeToday);
        model.addAllAttributes(initPage(model));

        model.addAttribute("board",b);
        return "infos/boards";
    }

    @GetMapping("/employees")
    public String employees(Model model) {
        model.addAllAttributes(initPage(model));


        return "infos/employees";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAllAttributes(initPage(model));


        return "infos/menu";
    }

    @GetMapping("/config")
    public String config(Model model) {
        model.addAllAttributes(initPage(model));


        return "infos/config";
    }

    private void initDash(String cnpj) {
        this.restaurant = restaurantService.restaurantByCnpj(cnpj);
        idRestaurant = restaurant.getId();
        
        List<Reserve> listReserve = reserveService.findReserveByDay(LocalDate.now());

        this.reservesToday = (int) listReserve.stream().count();

        this.clientToday = listReserve.stream().mapToInt(Reserve::getQuantity).sum();

        this.balanceToday = controlService.balanceToday(LocalDate.now());

        var r = reserveService.findBoardbyDate(LocalDate.now(), this.restaurant.getId());

        for (Board board : boardService.allBoards(idRestaurant)) {
            allBoardEntity.add(new BoardDTO(board.getNumber(), board.getCapacity()));
        }
        this.allBoards = allBoardEntity.size();

        if (r.size()==0) {
            this.boardsFreeToday =allBoards;
        }else{
            this.boardsFreeToday =allBoards-r.size();
        }
        

        this.clientsToday = listReserve.stream()
                .map(reserve -> {
                    if (reserve.getClient() != null && reserve.getClient().getName() != null) {
                        return Arrays.asList(reserve.getClient().getName(), reserve.getQuantity());
                    } else {
                        return Arrays.asList("Cliente desconhecido", reserve.getQuantity());
                    }
                })
                .collect(Collectors.toList());

    }
}
