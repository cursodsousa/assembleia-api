Para executar a aplicação com todos os recursos (banco de dados, etc), ter o docker compose instalado e utilizar o seguinte comando:

docker compose up

abrir no navegador a seguinte url:

http://localhost:8080

Sobre os extras:

Api de validação de CPFs está offline. Porém daria pra conectar com ela atráves do RestTemplate
ou então do Open Feign, fazer o request, obter o retorno e validar se está apto a votar.

Sobre serviço de mensageria:

Ia subir uma instancia via docker compose, criar uma fila de resultado,
Criar um schedule do Spring para verificar quais pautas encerraram e usar o 
RabbitTemplate para publicar uma mensagem com o resultado na fila