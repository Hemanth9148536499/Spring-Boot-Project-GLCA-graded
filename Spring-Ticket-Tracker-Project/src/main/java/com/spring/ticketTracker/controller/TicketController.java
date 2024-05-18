package com.spring.ticketTracker.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.ticketTracker.dto.TicketDto;
import com.spring.ticketTracker.service.TicketService;

import jakarta.validation.Valid;





@Controller
public class TicketController {

	private TicketService ticketService;

	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@GetMapping("/Admin/tickets")
	public String tickets(Model model) {
		List<TicketDto> tickets = ticketService.findAllTickets();
		model.addAttribute("tickets", tickets);
		return "/Admin/tickets";
	}

	@GetMapping("/Admin/tickets/newticket")
	public String newticketForm(Model model) {
		TicketDto ticketDto = new TicketDto();
		model.addAttribute("ticket", ticketDto);
		return "Admin/createticket";
	}
	@PostMapping("/Admin/tickets")
	public String createTicket(@Valid @ModelAttribute("ticket") TicketDto ticketDto, BindingResult result ,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("ticket",ticketDto);
			return "Admin/createticket";
		}
		ticketDto.setUrl(getUrl(ticketDto.getTitle()));
		ticketService.createTicket(ticketDto);
		return "redirect:/Admin/tickets";
	}
	
	private static String getUrl(String ticketTitle) {

		String title = ticketTitle.trim().toLowerCase();
		String url = title.replaceAll("\\s+", "-");
		url = url.replaceAll("[A-Za-z0-9]", "=");
		return url;
	}
	
	@GetMapping("/Admin/tickets/{ticketId}/edit")
	public String editTicketform(@PathVariable("ticketId") long ticketId ,Model model) {
		TicketDto ticketDto=ticketService.findTicketById(ticketId);
		model.addAttribute("ticket",ticketDto);
		return "Admin/editticket";
	}
	
	@PostMapping("/Admin/tickets/{ticketId}")
	public String updateTheTicket(@PathVariable("ticketId") Long ticketId,@Valid @ModelAttribute("ticket") TicketDto ticketDto
			,BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("ticket",ticketDto);
			return"Admin/editticket";
		}
		ticketDto.setId(ticketId);
		ticketService.updateTicket(ticketDto);
		return"redirect:/Admin/tickets";
		
	}
	
	@GetMapping("/Admin/tickets/{ticketId}/delete")
	public String delete(@PathVariable("ticketId")Long ticketId) {
		ticketService.deleteTicket(ticketId);
		return "redirect:/Admin/tickets";
	}
	
	@GetMapping("/Admin/tickets/{ticketUrl}/view")
	public String viewPost(@PathVariable("ticketUrl") String ticketUrl,Model model) {
		TicketDto ticketDto=ticketService.findTicketByUrl(ticketUrl);
		model.addAttribute("ticket",ticketDto);
		return"Admin/viewTicket";
	}
	
	@GetMapping("/Admin/tickets/search")
	public String searchTickets(@RequestParam(value="query") String query,Model model) {
		List<TicketDto> ticketDto=ticketService.searchTickets(query);
		model.addAttribute("tickets",ticketDto);
		return"Admin/tickets";
	}
}
