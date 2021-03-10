package ru.site.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.site.model.Event;
import ru.site.model.Request;
import ru.site.repository.EventCustomRepository;
import ru.site.repository.EventRepository;
import ru.site.repository.RequestRepository;

import java.util.*;

@Controller
public class MainController {

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EventCustomRepository eventCustomRepository;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home(Model model) {
		Iterable<Request> requests = requestRepository.findAll();
		model.addAttribute("requests", requests);
		return "index";
	}

	@GetMapping("/add")
	public String GetRequestAdd() {
		return "request-add";
	}

	@PostMapping("/add")
	public String PostRequestAdd(@RequestParam String head, @RequestParam String mytext) {
		Request request = new Request(head, mytext);
		requestRepository.save(request);
		return "redirect:/";
	}

	@GetMapping("/request/{id}")
	public String GetRequestDetail(@PathVariable(value = "id") Long id, Model model) {
		if (!requestRepository.existsById(id)) {
			return "redirect:/";
		}
		Optional<Request> myRequestOptional = requestRepository.findById(id);
		ArrayList<Request> result = new ArrayList<>();
		myRequestOptional.ifPresent(result::add);
		model.addAttribute("request", result);
		return "request-details";
	}

	@GetMapping("/request/{id}/edit")
	public String GetRequestEdit(@PathVariable(value = "id") Long id, Model model) {
		if (!requestRepository.existsById(id)) {
			return "redirect:/";
		}
		Optional<Request> myRequest = requestRepository.findById(id);
		ArrayList<Request> result = new ArrayList<>();
		myRequest.ifPresent(result::add);
		model.addAttribute("request", result);
		return "request-edit";
	}


	@PostMapping("/request/{id}/edit")
	public String PostRequestEdit(@PathVariable(value = "id") Long id, @RequestParam String head, @RequestParam String mytext) {
		Request request = requestRepository.findById(id).orElseThrow();
		request.setText(mytext);
		request.setHead(head);
		requestRepository.save(request);
		return "redirect:/";
	}

	@PostMapping("/request/{id}/remove")
	public String RemoveRequest(@PathVariable(value = "id") Long id) {
		Request request = requestRepository.findById(id).orElseThrow();
		requestRepository.delete(request);
		return "redirect:/";
	}

	@GetMapping("/calendar")
	public String GetCalendar(@RequestParam(value = "date", required = false) String dateOrNUll, Model model) {
		Iterable<Event> events = eventRepository.findAll();
		List<Event> spisok = new ArrayList<>();
		for (Event iventejer : events) {
			spisok.add(iventejer);
		}
		model.addAttribute("spisok", spisok);
		if (dateOrNUll == null) {
			return "calendar";
		}
		int kol=0;
		int day=0,month=0,year=0;

		List <String> ListOfDays = new ArrayList<>();
		String MyNumber = "";

		for (char ch:dateOrNUll.toCharArray()){
			if (ch=='.'){
				ListOfDays.add(MyNumber);
				MyNumber = "";
			}else{
				MyNumber = MyNumber + ch;
			}
		}
		ListOfDays.add(MyNumber);
		for(String numb : ListOfDays) {
			kol++;
			if (kol==1) {day = Integer.parseInt(numb);}
			if (kol==2) {month = Integer.parseInt(numb)-1;}
			if (kol==3) {year = Integer.parseInt(numb);}
		}
        Calendar calendar = new GregorianCalendar(year,month,day);
        Date date = calendar.getTime();
		List<Event> MyList = eventCustomRepository.FindByDate(date);
//        for (Event iventejer : MyList) {
//			MyList.add(iventejer);
//		}
        model.addAttribute("MyList", MyList);
		model.addAttribute("MyDate", date);
        return "calendar-detail";
	}
	@PostMapping("/calendar")
	public String PostCalendar(@RequestParam(value = "date") String date){

		int kol=0;
		int day=0,month=0,year=0;
		List <String> ListOfDays = new ArrayList<>();
		String MyNumber = "";

		for (char ch:date.toCharArray()){
			if (ch=='.'){
				ListOfDays.add(MyNumber);
				MyNumber = "";
			}else{
				MyNumber = MyNumber + ch;
			}
		}
		ListOfDays.add(MyNumber);
		for(String numb : ListOfDays) {
			kol++;
			if (kol==1) {day = Integer.parseInt(numb);}
			if (kol==2) {month = Integer.parseInt(numb)-1;}
			if (kol==3) {year = Integer.parseInt(numb);}
		}
		return "calendar-detail";
	}

	@GetMapping("/calendar/{date}")
	public String GetCalendarDate(@PathVariable(value = "date") String date, Model model) {
		Iterable<Event> events = eventRepository.findAll();
		List<Event> spisok = new ArrayList<Event>();
		for (Event iventejer : events) {
			if (iventejer.getDate().equals(date)) {
				spisok.add(iventejer);
			}
		}
		model.addAttribute("spisok", spisok);
		return "calendar-detail";
	}

	/*@PostMapping("/calendar/")
	public String addivent(@RequestParam Date dateString, @RequestParam String ivent){
		String[] numberarray = dateString.split(";");
		int kol=0;
		int day=0,month=0,year=0;
		int colich=numberarray.length;
		for(String numb : numberarray) {
			kol++;
			if (kol==1) {day = Integer.parseInt(numb);}
			if (kol==2) {month = Integer.parseInt(numb)-1;}
			if (kol==3) {year = Integer.parseInt(numb);}
		}
		System.out.println(day);
		System.out.println(month);
		System.out.println(year);
		Calendar calendar = new GregorianCalendar(year,month,day);
		Date date = calendar.getTime();
		System.out.println(date);
		Ivent ivent1 = new Ivent(date,ivent);
		iventRepository.save(ivent1);
		return "redirect:/calendar/"+dateString;
	}*/


	@GetMapping("/calendar/{date}/edit/{id}")
	public String GetCalendarEventEdit(@PathVariable(value = "date") String date, @PathVariable(value = "id") Long id, Model model) {
		if (!eventRepository.existsById(id)) {
			return "redirect:/calendar/" + date;
		}
		Optional<Event> event = eventRepository.findById(id);
		ArrayList<Event> result = new ArrayList<>();
		event.ifPresent(result::add);
		model.addAttribute("event", result);
		return "calendar-edit";
	}

	@PostMapping("/calendar/{date}/edit/{id}")
	public String PostCalendarEventEdit(@PathVariable(value = "date") String date, @PathVariable(value = "id") Long id, @RequestParam String events, Model model) {
		Event event = eventRepository.findById(id).orElseThrow();
		event.setEvent(events);
		eventRepository.save(event);
		return "redirect:/calendar/" + date;
	}

	@PostMapping("/calendar/{date}/remove/{id}")
	public String PostRemoveCalendar(@PathVariable(value = "date") String date, @PathVariable(value = "id") Long id, Model model) {
		Event event = eventRepository.findById(id).orElseThrow();
		eventRepository.delete(event);
		return "redirect:/calendar/" + date;
	}

}
