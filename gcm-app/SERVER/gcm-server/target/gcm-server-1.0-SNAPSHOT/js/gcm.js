/*
 * (C) Copyright ${year} Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Iasmim Ribeiro
 */

var gcm = (function(instancia) {

    var TIME_TO_REFRESH_COUNTER = 1000;
    var SUCESS_ICON = "img/sucess.png";
    var DANGER_ICON = "img/danger.png";
    var WARNING_ICON = "img/warning.png";
    var INFO_ICON = "img/info.png";

    if (instancia) {
        return instancia;
    }

    var registreEventos = function() {
        /* Evento para mostrar o formulário de cadastro de grupos */
        $("#adicionar_grupo").on({
            click: function() {
                $("#form_cadastro_grupos").fadeIn();
            }
        });

        /* Exibe mensagem de erro se nenhum grupo tiver sido selecionado ao tentar inserir uma mensagem */
        $("div.gcm-text > a").on({
            click: function() {
                if ($(this).parent().data('selected') === false) {
                    exibaAlerta(3, "Primeiro selecione um grupo e adicione uma mensagem.", WARNING_ICON, 3500);
                    return false;
                }
            }
        });

        /* Remove a div de prevenção de inserção de texto sobre o textarea */
        $("div#hidden").on({
            click: function() {
                if ($(this).parent().data('selected') === false) {
                    exibaAlerta(3, "Primeiro selecione um grupo.", WARNING_ICON, 3500);
                    return false;
                } else {
                    $(this).remove();
                    $("textarea").prop('disabled', false).focus();
                }
            }
        });

        /* Obtém as informações do grupo quando selecionado no dropdown */
        $("ul.dropdown-menu li").on({
            click: function() {
                obtenhaInformacoesGrupo($(this));
            }
        });

        /* Evento para confirmar o cadastro do grupo */
        $("#cadastrar").on({
            click: function() {
                $.ajax({
                    url: "AjaxRequestHandler",
                    type: 'POST',
                    data: {type: 2, data: JSON.stringify(obtenhaJSON())},
                    beforeSend: function() {
                        exibaAlerta(2, "Requisição de cadatro enviada para o servidor com sucesso!", INFO_ICON);
                    },
                    success: function(data) {
                        if (data.sucesso) {
                            exibaAlerta(1, "Grupo cadastrado com sucesso!", SUCESS_ICON);
                            $("ul.dropdown-menu")
                                    .append("<li data-id=" + data.groupId + "><a href='#'>" + $('input.form-control').val() + "</a></li>")
                                    .on({click: function() {
                                            obtenhaInformacoesGrupo($(this).find('li:last-child'));
                                        }});

                            $("a.dropdown-toggle")
                                    .css('background-color', '#eee');
                            /* Adiciona efeito para dar destaque ao drop down de grupos */
                            setTimeout(function() {
                                $("a.dropdown-toggle").css('background-color', '');
                            }, 300);

                            $("#cadastrar").parent().prev().prev().val('');
                        } else {
                            exibaAlerta(4, "Alguma exceção ocorreu no servidor: " + data.exception, DANGER_ICON, 8000);
                        }
                    }
                });
            }
        });

        $("textarea").on({
            keypress: function() {
                console.log($("textarea").val().length * 8);
            }
        });

        $(".gcm-text a.btn").on({
            click: function() {
                var object = {
                    message: $(".gcm-text textarea").val(),
                    id: $("label.group-name").attr('groupid')
                };

                $.ajax({
                    url: "AjaxRequestHandler",
                    type: 'POST',
                    data: {type: 4, data: JSON.stringify(object)},
                    beforeSend: function() {
                        exibaAlerta(2, "Requisição de cadatro enviada para o servidor com sucesso!", INFO_ICON);
                    },
                    success: function(data, textStatus, xhr) {
                        debugger;
                        console.log(data);
                    }
                });
            }

        });
    },
            obtenhaJSON = function() {
                var result = {
                    list: []
                };

                result["group_name"] = $("input.form-control").val();
                $("div.ms-selection ul.ms-list > li.ms-selected").each(function(index) {
                    result.list[index] = $(this).find('span').attr('data-value');
                });

                return result;
            },
            obtenhaQuantidadeGrupos = function() {
                $.ajax({
                    url: "AjaxRequestHandler",
                    type: 'POST',
                    data: {type: 1},
                    success: function(data) {
                        $("span.badge").text(data);
                    }
                });
            },
            limpeDivAlerta = function() {
                $("div.alert").find("img").attr("src", "");
                $("div.alert").find("span").text("");

                $("div.alert").css({'display': 'none', 'margin-bottom': '20px', 'opacity': '1'});
            },
            exibaAlerta = function(tipo, mensagem, icone, time_to_dismis) {
                var TIME_TO_TOAST_DISMIS = (time_to_dismis !== null || time_to_dismis !== undefined) ? time_to_dismis : 2000;

                if (icone !== null || icone !== undefined) {
                    $("div.alert").find("img").attr("src", icone);
                }

                $("div.alert").find("span").text(mensagem);

                window.estilo = "";
                switch (tipo) {
                    case 1:
                        estilo = "alert-success";
                        break;
                    case 2:
                        estilo = "alert-info";
                        break;
                    case 3:
                        estilo = "alert-warning";
                        break;
                    case 4:
                        estilo = "alert-danger";
                        break;
                }

                $("div.alert").addClass(estilo);
                $("div.alert")
                        .show()
                        .stop()
                        .delay(500)
                        .animate({marginBottom: '60px', opacity: '0'}, TIME_TO_TOAST_DISMIS, function() {
                            limpeDivAlerta();
                        });
            },
            obtenhaInformacoesGrupo = function(campo) {
                var name = $(campo).text();
                var result = { id: $(campo).data('id') };
                
                $.ajax({
                    url: "AjaxRequestHandler",
                    type: 'POST',
                    data: {type: 3, data: JSON.stringify(result)},
                    beforeSend: function() {
                        exibaAlerta(2, "Requisição enviada ao servidor!", INFO_ICON);
                    },
                    success: function(data, textStatus, xhr) {
                        $("div.gcm-text > div label.badge").text(data);
                        $("label.group-name").attr('groupid', campo.data('id')).text(name).addClass('destaque');
                        $(".gcm-text").data('selected', true);
                        $(".hidden").remove();
                        $("textarea").prop('disabled', false);
                    }
                });
            };

    /* Document ready */
    $(document).on({
        ready: function() {
            $('#my-select').multiSelect();
            registreEventos();
        }
    });

    /* Adiciona o evento setInterval ao window para atualizar a quantidade de grupos */


    return {
        obtenhaJSON: obtenhaJSON,
        exibaAlerta: exibaAlerta
    };

})(gcm || undefined);

