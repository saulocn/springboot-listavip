package br.com.alura.listavip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alura.enviadorEmail.enviadorEmail.EmailService;
import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.service.ConvidadoService;

@Controller
public class ConvidadoController {
	@Autowired
	private ConvidadoService service;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("listaconvidados")
	public String listaConvidados(Model model) {
		Iterable<Convidado> convidados = service.obterTodos();
		model.addAttribute("convidados", convidados);
		return "listaconvidados";
	}

	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(Model model, @RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("telefone") String telefone) {
		Convidado novoConvidado = new Convidado(nome, email, telefone);
		service.salvar(novoConvidado);
		new EmailService().enviar(nome, email);
		Iterable<Convidado> convidados = service.obterTodos();
		model.addAttribute("convidados", convidados);
		return "listaconvidados";
	}

	public void obterConvidadoPor(String nome) {
		service.buscarPorNome(nome);
	}
}
