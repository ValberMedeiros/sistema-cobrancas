package com.sistema.cobranca.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.cobranca.cobranca.model.StatusTitulo;
import com.sistema.cobranca.cobranca.model.Titulo;
import com.sistema.cobranca.cobranca.repository.TituloRepository;
import com.sistema.cobranca.cobranca.repository.filter.TituloFilter;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private TituloRepository tr;
	
	@RequestMapping("/novo")
	public ModelAndView titulo() {
		ModelAndView modelAndView = new ModelAndView("CadastroTitulo");
		modelAndView.addObject(new Titulo());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if(errors.hasErrors()) {
			return "CadastroTitulo";
		}
		
		try {
			tr.save(titulo);		
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso");
			return "redirect:/titulos/novo";
		} catch (DataIntegrityViolationException e) {
			errors.rejectValue("dataVencimento", null, "Data infomada está com o formato inválido");
			return "redirect:/titulos/novo";
		}
	}
	
	@RequestMapping
	public ModelAndView listar(@ModelAttribute("filtro")TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null? "" : filtro.getDescricao();
		List<Titulo> todosTitulos = tr.findByDescricaoContainingOrderByDescricaoAsc(descricao);
		ModelAndView modelAndView = new ModelAndView("PesquisaTitulos");
		modelAndView.addObject("todosTitulos" , todosTitulos);
		return modelAndView;
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Titulo titulo) {
		ModelAndView modelAndView = new ModelAndView("CadastroTitulo");
		String statusSelect = titulo.getStatus().getDescricao();
		modelAndView.addObject("titulo", titulo);
		modelAndView.addObject("statusSelecionado", statusSelect);
		
		return modelAndView;
	}
	
	@RequestMapping("/deletar/{id}")
	public String excluir(@PathVariable("id") Titulo titulo, RedirectAttributes attributes) {
		tr.delete(titulo);
		attributes.addFlashAttribute("mensagem", "Título deletado com sucesso");
		return "redirect:/titulos";
	}
	
	@GetMapping("/recebido/{id}")
	public String marcarComoRecebido(@PathVariable("id") Titulo titulo) {
		titulo.setStatus(StatusTitulo.RECEBIDO);
		tr.save(titulo);
		return "redirect:/titulos";
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
	
	@ModelAttribute("todosTitulos")
	public List<Titulo> todosTitulos (){
		List<Titulo> titulos = (List<Titulo>) tr.findAll();
		return titulos;
	}
}
