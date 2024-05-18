package com.spring.ticketTracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.spring.ticketTracker.dto.TicketDto;
import com.spring.ticketTracker.entity.Ticket;
import com.spring.ticketTracker.mapper.TicketMapper;
import com.spring.ticketTracker.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	
	private TicketRepository ticketRepository;
	
	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository=ticketRepository;
	}
	
	@Override
	public List<TicketDto> findAllTickets() {
		List<Ticket> tickets=ticketRepository.findAll();
		return tickets.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toList());
	}

	@Override
	public void createTicket(TicketDto ticketDto) {
		Ticket ticket=TicketMapper.mapToTicket(ticketDto);
		ticketRepository.save(ticket);
		
	}

	@Override
	public TicketDto findTicketById(Long id) {
		Ticket ticket=ticketRepository.findById(id).get();
		return TicketMapper.mapToTicketDto(ticket);
	}

	@Override
	public void updateTicket(TicketDto ticketDto) {
		Ticket ticket=TicketMapper.mapToTicket(ticketDto);
		ticketRepository.save(ticket);
		
	}

	@Override
	public void deleteTicket(Long id) {
		ticketRepository.deleteById(id);
		
		
	}

	@Override
	public TicketDto findTicketByUrl(String ticketUrl) {
		Ticket ticket=ticketRepository.findByUrl(ticketUrl).get();
		return TicketMapper.mapToTicketDto(ticket);
	}

	@Override
	public List<TicketDto> searchTickets(String query) {
		List<Ticket> ticket=ticketRepository.searchTickets(query);
		return ticket.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toList());
	}

	

}
