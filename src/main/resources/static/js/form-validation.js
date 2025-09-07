function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]+/g, '');
    if (cpf == '') return false;
    // Elimina CPFs invalidos conhecidos
    if (cpf.length != 11 ||
        cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" ||
        cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" ||
        cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" ||
        cpf == "99999999999")
        return false;
    // Valida 1o digito
    let add = 0;
    for (let i = 0; i < 9; i++) add += parseInt(cpf.charAt(i)) * (10 - i);
    let rev = 11 - (add % 11);
    if (rev == 10 || rev == 11) rev = 0;
    if (rev != parseInt(cpf.charAt(9))) return false;
    // Valida 2o digito
    add = 0;
    for (let i = 0; i < 10; i++) add += parseInt(cpf.charAt(i)) * (11 - i);
    rev = 11 - (add % 11);
    if (rev == 10 || rev == 11) rev = 0;
    if (rev != parseInt(cpf.charAt(10))) return false;
    return true;
}


$(document).ready(function() {

    // --- Validação do NOME ---
    const nomeInput = document.getElementById('nmUsuario');
    if (nomeInput) {
        nomeInput.addEventListener('input', function() {
            const valor = nomeInput.value;
            const valorFiltrado = valor.replace(/[^A-Za-záàâãéèêíïóôõöúçÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇ ]/g, '');
            if (valor !== valorFiltrado) {
                nomeInput.value = valorFiltrado;
            }
        });
    }

    // --- Validação da SENHA ---
    const senhaInput = document.getElementById('senha');
    if (senhaInput) {
        const passwordHelpBlock = document.getElementById('passwordHelpBlock');
        const rules = {
            length: document.getElementById('length'),
            lowercase: document.getElementById('lowercase'),
            uppercase: document.getElementById('uppercase'),
            number: document.getElementById('number'),
            special: document.getElementById('special')
        };

        senhaInput.addEventListener('focus', function() {
            passwordHelpBlock.classList.add('is-visible');
        });

        senhaInput.addEventListener('blur', function() {
            const allValid = Object.values(rules).every(rule => rule && rule.classList.contains('valid'));
            if (allValid) {
                passwordHelpBlock.classList.remove('is-visible');
            }
        });

        senhaInput.addEventListener('input', function() {
            const serverError = document.getElementById('senha-server-error');
            if (serverError) {
                serverError.style.display = 'none';
            }
            const senha = this.value;
            const validate = (element, condition) => {
                if (element) {
                    if (condition) {
                        element.classList.replace('invalid', 'valid');
                    } else {
                        element.classList.replace('valid', 'invalid');
                    }
                }
            };
            validate(rules.length, senha.length >= 8);
            validate(rules.lowercase, /[a-z]/.test(senha));
            validate(rules.uppercase, /[A-Z]/.test(senha));
            validate(rules.number, /\d/.test(senha));
            validate(rules.special, /[@$!%*?&]/.test(senha));
        });
    }

    // --- Validação do CPF ---
    const cpfInput = $('#CPF');
    if (cpfInput.length) {
        const cpfErrorDiv = $('#cpf-client-error');

        cpfInput.mask('000.000.000-00');

        cpfInput.on('blur', function() {
            const cpf = $(this).val();
            if (cpf.length === 0) {
                cpfErrorDiv.hide();
            } else if (cpf.length === 14) {
                if (validarCPF(cpf)) {
                    cpfErrorDiv.hide();
                } else {
                    cpfErrorDiv.text('CPF inválido.').show();
                }
            } else {
                cpfErrorDiv.text('CPF incompleto.').show();
            }
        });
    }

});