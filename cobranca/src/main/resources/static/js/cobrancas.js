$('#modalDelete').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);

	var idTitulo = button.data('codigo');
	var descricaoTitulo = button.data('descricao');

	var modal = $(this);
	var form = modal.find('form');
	var p = modal.find('p')
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + idTitulo);
	
	modal.find('.modal-body span').html('Tem certeza que deseja apagar o título <strong>' + descricaoTitulo + '</strong>');
});

$(function() {
	$('[rel="tooltip"]').tooltip();
});

$(document).ready(function(e) {
    $("#dataVencimento").datepicker({
        dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        dateFormat: 'dd/mm/yy',
        nextText: 'Proximo',
        prevText: 'Anterior'
    });
});