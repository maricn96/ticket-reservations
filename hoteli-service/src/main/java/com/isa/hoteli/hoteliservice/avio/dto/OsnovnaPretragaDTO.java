package com.isa.hoteli.hoteliservice.avio.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OsnovnaPretragaDTO
{
	private LocalDateTime time1;
	private LocalDateTime time2;
	private Long takeOffDestination;
	private Long landingDestination;
	private Integer number; //broj preostalih mesta
}
