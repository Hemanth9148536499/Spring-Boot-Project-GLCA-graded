package com.spring.ticketTracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.ticketTracker.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
	Optional<Ticket> findByUrl(String url);
	@Query("SELECT t FROM Ticket t WHERE "
			+" t.title LIKE CONCAT('%', :query, '%') OR "
			+" t.shortDescription LIKE CONCAT('%', :query, '%')")
	
	List<Ticket> searchTickets(String query);

}