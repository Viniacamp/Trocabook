document.addEventListener('DOMContentLoaded', function() {
    const senhaInput = document.getElementById('senha');

    if (!senhaInput) {
        return;
    }

    const passwordHelpBlock = document.getElementById('passwordHelpBlock');

    senhaInput.addEventListener('focus', function() {
        passwordHelpBlock.classList.add('is-visible');
    });

    const rules = {
        length: document.getElementById('length'),
        lowercase: document.getElementById('lowercase'),
        uppercase: document.getElementById('uppercase'),
        number: document.getElementById('number'),
        special: document.getElementById('special')
    };

    // Este é o evento que dispara a cada tecla digitada
    senhaInput.addEventListener('input', function() {
        // ===== NOVO CÓDIGO AQUI =====
        // Procura pela div de erro do servidor
        const serverError = document.getElementById('senha-server-error');
        // Se a div existir, esconde ela
        if (serverError) {
            serverError.style.display = 'none';
        }
        // ===== FIM DO NOVO CÓDIGO =====


        // O código de validação em tempo real que já existia continua aqui
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
});