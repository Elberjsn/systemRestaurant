package com.elberjsn.restaurant.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ReserveDTO {
    private Long reserveId;
    private LocalDate dtReserve;
    private LocalTime hrStart;
    private Long boardId;
    private String boardName;

    public ReserveDTO(Long reserveId, LocalDate dtReserve, Long boardId, String boardName,LocalTime hrStart) {
        this.reserveId = reserveId;
        this.dtReserve = dtReserve;
        this.boardId = boardId;
        this.boardName = boardName;
        this.hrStart = hrStart;
    }
}
