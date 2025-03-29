package com.elberjsn.restaurant.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elberjsn.restaurant.models.Control;
import com.elberjsn.restaurant.models.utils.StatusControl;
import com.elberjsn.restaurant.repository.ControlRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ControlService {

    @Autowired
    ControlRepository controlRepository;

    @Autowired
    ConsumptionService consumptionService;

    @Autowired
    ReserveService reserveService;

    public Control initControl(Control control) {
        
        var c = controlRepository.save(control);
        if (c.getId() != null) {
            var r = control.getReserve();
            r.setControl(c);
            reserveService.initControl(r);
        }
        return c;
    }

    public Control controlById(Long id) {
        return controlRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Não Encontrado!"));
    }

    public int countControls() {
        return (int) controlRepository.findAll().stream().count();
    }

    public Control controlByNumber(int number) {
        return controlRepository.findByNumber(number).orElseThrow(() -> new EntityNotFoundException("Não Encontrado!"));
    }

    public Control openControl(Long idReserve) {
        var reserve = reserveService.findById(idReserve);
        Control control = new Control();
        control.setDtOpen(LocalDateTime.now());
        control.setNumber(countControls());
        control.setReserve(reserve);

        return initControl(control);
    }

    public Double valueTotalControl(Long controlId) {
        return consumptionService.valueTotalByControlId(controlId);
    }

    public Control editControl(Control control) {
        var newControl = controlById(control.getId());
        newControl.setDtOpen(control.getDtOpen());
        newControl.setDtClosed(control.getDtClosed());
        newControl.setStatus(control.getStatus());
        newControl.setPaymentMethod(control.getPaymentMethod());
        newControl.setReserve(control.getReserve());

        return controlRepository.save(newControl);
    }

    public void paymentControl(Long id, String pay) {
        var payControl = controlById(id);

        payControl.setDtClosed(LocalDateTime.now());
        payControl.setPaymentMethod(pay);
        payControl.setStatus(StatusControl.PAY);
    }

    public void deleteControl(Long id) {
        Control c = controlById(id);
        c.setStatus(StatusControl.CLOSED);
        controlRepository.save(c);
    }

    public Double balanceToday(LocalDate dt) {
        var c = controlRepository.findByDtClosedBetween(dt.atTime(LocalTime.MIN), dt.atTime(LocalTime.MAX));
        return c.stream().mapToDouble(Control::getTotalValue).sum();
    }
}