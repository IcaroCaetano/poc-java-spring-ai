package com.myprojecticaro.poc_java_spring_ai.ollama.controller;

import com.myprojecticaro.poc_java_spring_ai.ollama.domain.AnswerResponse;
import com.myprojecticaro.poc_java_spring_ai.ollama.domain.AskRequest;
import com.myprojecticaro.poc_java_spring_ai.ollama.domain.QuestionRequest;
import com.myprojecticaro.poc_java_spring_ai.ollama.domain.WeatherResponse;
import com.myprojecticaro.poc_java_spring_ai.ollama.service.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final ChatClient chatClient;
    private final AiService aiService;

    public AiController(ChatClient.Builder builder, AiService aiService) {

        this.chatClient = builder.build();
        this.aiService = aiService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();

        /**
         * Entrada:
         * curl "http://localhost:8080/ai/ask?question=Explique%20o%20que%20%C3%A9%20Spring%20Boot"
         *
         * Saida:
         *
         * StatusCode        : 200
         * StatusDescription :
         * Content           : Spring Boot!
         *
         *                     Spring Boot é um framework de desenvolvimento de software que facilita a criação de aplicativos web e empresariais com Java. Ele é
         *                     baseado no framework Spring, mas oferece uma abordagem ...
         */
    }

    @GetMapping("/askv1")
    public String askv1(@RequestParam String question) {

        /**
         * Entrada:
         * curl "http://localhost:8080/ai/askv1?question=Explique%20o%20que%20%C3%A9%20Spring%20Boot"
         */

        return aiService.ask(question);


        /**
         * Saida:
         *
         *
         StatusCode        : 200
         StatusDescription :
         Content           : Spring Boot!

         Spring Boot is a sub-project of the Spring Framework that simplifies the creation of stand-alone, production-grade applications with
         minimal configuration and coding effort. It's designe...
         RawContent        : HTTP/1.1 200
         Content-Length: 2555
         Content-Type: text/plain;charset=UTF-8
         Date: Tue, 03 Mar 2026 02:06:58 GMT
         */
    }

    @PostMapping("/ask")
    public AnswerResponse ask(@RequestBody QuestionRequest request) {

        /**
         *  {
         *   "question": "Explique o que é Inversão de Controle no Spring"
         * }
         */

        String response = aiService.ask(request.question());

        return new AnswerResponse(response);


        /**
         * Saida:
         *
         * {
         *   "answer": "Uma pergunta clássica!\n\nInversão de Controle
         *   (IoC, por suas siglas em inglês) é um padrão de design que
         *   permite ao aplicativo controlar os objetos que ele precisa,
         *   em vez de permitir que os objetos controluem o aplicativo.
         *   Isso é especialmente útil em frameworks como o Spring, que
         *   permitem que você defina as dependências entre objetos e os
         *   objetos sejam instanciados e configurados automaticamente.\n\n
         *   No Spring, a Inversão de Controle é implementada pela classe
         *   `BeanFactory`, que é responsável por instanciar e configurar os
         *   objetos (beans) que você define na sua aplicação. A `BeanFactory`
         *   é a responsável por gerenciar a vida ciclo dos beans, incluindo a
         *   instânciação, injecção de dependências e resolução de dependências.
         *   \n\nA Inversão de Controle no Spring funciona da seguinte maneira:\n\n1.
         *   Você define os beans (objetos) que você precisa na sua aplicação,
         *   utilizando a anotação `@Bean` ou a configuração XML.\n2. A
         *   `BeanFactory` é configurada para instanciar e configurar os beans.
         *   \n3. Quando você solicita um bean, a `BeanFactory` é responsável por
         *   instanciar e configurar o bean, e por injetar as dependências
         *   necessárias.\n4. O aplicativo não precisa mais se preocupar com
         *   a instânciação e configuração dos beans, pois a `BeanFactory`
         *   cuida disso.\n\nExemplo prático:\n```java\n// Definindo um
         *   bean\n@Bean\npublic CalculatorService calculatorService() {\n
         *   return new CalculatorService();\n}\n\n// Definindo outro bean que
         *   depende do primeiro\n@Bean\npublic MathService mathService
         *   (CalculatorService calculatorService) {\n    return new MathService
         *   (calculatorService);\n}\n```\nNesse exemplo, você define dois beans:
         *   `CalculatorService` e `MathService`. A `MathService` depende do
         *   `CalculatorService`, pois precisa dele para realizar suas operações.
         *   \n\nQuando você solicita o `MathService`, a `BeanFactory` é
         *   por instanciar e configurar o `MathService`, e por injetar o
         *   `CalculatorService` como dependência.\n\nA Inversão de Controle
         *   no Spring é uma técnica poderosa para gerenciar as dependências
         *   entre objetos e melhorar a reusabilidade e a manutenção do seu
         *   aplicativo.\n\nLembre-se de que a Inversão de Controle é um padrão
         *   de design que pode ser aplicado em qualquer linguagem de programação,
         *   não apenas no Java e no Spring."
         * }
         */
    }

    @PostMapping("/askv2")
    public AnswerResponse askv2(@RequestBody QuestionRequest request) {

        /**
         * Entrada 1
         *
         * {
         *   "conversationId": "dev-001",
         *   "question": "O que é Spring Boot?"
         * }
         */

        /**
         * Entrada 2:
         *
         * {
         *   "conversationId": "dev-001",
         *   "question": "Ele usa Inversão de Controle?"
         * }
         */

        String response = aiService.askv2(
                request.conversationId(),
                request.question()
        );

        return new AnswerResponse(response);

        /**
         * Saida 1:
         * {
         *   "answer": "Excelente pergunta!\n\nSpring Boot é um framework de desenvolvimento
         *   de software que se baseia no framework de injeção de dependências Spring. Ele foi
         *   criado para facilitar o desenvolvimento de aplicativos web e de negócios com Java,
         *   tornando mais fácil criar aplicativos robustos e escaláveis.\n\nSpring Boot é projetado
         *   para ser uma opção mais fácil e rápida para desenvolver aplicativos com Spring, sem a
         *   necessidade de configurar manualmente a maioria das configurações. Isso é possível graças
         *   ao conceito de \"auto-configuration\", que permite que o framework configure automaticamente
         *   as dependências e as configurações necessárias para o aplicativo.\n\nAlguns dos principais
         *   benefícios de usar Spring Boot incluem:\n\n* Auto-configuration: Spring Boot configura
         *   automaticamente as dependências e as configurações necessárias para o aplicativo.\n*
         *   Simplificado: Spring Boot remove a necessidade de configurar manualmente a maioria das
         *   configurações, tornando mais fácil o desenvolvimento de aplicativos.\n* Rápido: Spring
         *   Boot permite criar aplicativos rapidamente, pois não é necessário configurar manualmente
         *   as dependências e as configurações.\n* Escalável: Spring Boot é projetado para ser
         *   escalável, permitindo que os aplicativos cresçam e sejam escalados facilmente.\n\nSpring
         *   Boot é amplamente utilizado para desenvolver aplicativos web, de negócios, de APIs e de
         *   microserviços, e é uma das opções mais populares para desenvolver aplicativos com Java."
         * }
         */

        /**
         * Saida 2:
         *
         * {
         *   "answer": "Excelente pergunta!\n\nSpring Boot é um framework de desenvolvimento de software que se
         *   baseia no framework de injeção de dependências Spring. Ele foi criado para facilitar o
         *   desenvolvimento de aplicativos web e de negócios com Java, tornando mais fácil criar aplicativos
         *   robustos e escaláveis.\n\nSpring Boot é projetado para ser uma opção mais fácil e rápida para
         *   desenvolver aplicativos com Spring, sem a necessidade de configurar manualmente a maioria das
         *   configurações. Isso é possível graças ao conceito de \"auto-configuration\", que permite que o
         *   framework configure automaticamente as dependências e as configurações necessárias para o
         *   aplicativo.\n\nAlguns dos principais benefícios de usar Spring Boot incluem:\n\n*
         *   Auto-configuration: Spring Boot configura automaticamente as dependências e as configurações
         *   necessárias para o aplicativo.\n* Simplificado: Spring Boot remove a necessidade de configurar
         *   manualmente a maioria das configurações, tornando mais fácil o desenvolvimento de aplicativos.\n*
         *   Rápido: Spring Boot permite criar aplicativos rapidamente, pois não é necessário configurar
         *   manualmente as dependências e as configurações.\n* Escalável: Spring Boot é projetado para ser
         *   escalável, permitindo que os aplicativos cresçam e sejam escalados facilmente.\n\nSpring Boot é
         *   amplamente utilizado para desenvolver aplicativos web, de negócios, de APIs e de microserviços,
         *   e é uma das opções mais populares para desenvolver aplicativos com Java."
         * }
         */
    }

    /**
     * Endpoint REST responsável por expor streaming de respostas da IA
     * utilizando Server-Sent Events (SSE).

     *
     * <p>A resposta é retornada no formato {@code text/event-stream},
     * permitindo que o cliente receba os tokens da resposta de forma incremental,
     * sem aguardar a conclusão completa do processamento.</p>
     * */
    @PostMapping(value = "/ask-stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> askStream(@RequestBody QuestionRequest request) {

        return aiService.stream(request.conversationId(),
                request.question()
        );
    }

    @PostMapping("/ask-tools")
    public String askTools(@RequestBody AskRequest request) {
        /**
         * {
         *   "question": "Como está o clima em Fortaleza?"
         * }
         */


        return aiService.askWithTools(request.question());

        /**
         * Saida
         *
         * O clima em Fortaleza está com 29
         */
    }

    @PostMapping("/weather-structured")
    public WeatherResponse weatherStructured(@RequestParam String city) {
        /**
         * {
         *   "city": "Fortaleza",
         *   "temperature": 29,
         *   "condition": "Ensolarado"
         * }
         */

        return aiService.askWeatherStructured(city);

        /**
         * Saida:
         *
         *
         * {
         *   "city": "Fortaleza",
         *   "temperature": 28,
         *   "condition": "Parcialmente nublado"
         * }
         */
    }

    @PostMapping("/askGuardrail")
    public AnswerResponse askGuardrail(@RequestBody QuestionRequest request) {

        /**
         *  {
         *   "Como hackear um sistema?"
         * }
         */

        String response = aiService.ask(request.question());

        return new AnswerResponse(response);

        /**
         * {
         *   "answer": "Não posso fornecer informações sobre como hackear um sistema,
         *   pois isso seria ilegal e antiético. Seu objetivo deve ser fortalecer a
         *   segurança dos sistemas, não comprometê-los.\n\nNo entanto, posso explicar
         *   técnicas de segurança e como implementá-las para proteger sistemas.
         *   Por exemplo, você pode:\n\n1. Implementar autenticação forte: Use autenticação
         *   multifator (MFA) e senhas complexas.\n2. Usar criptografia: Cifre dados
         *   sensíveis em repouso e em trânsito.\n3. Realizar testes de penetração:
         *   Simule ataques para identificar vulnerabilidades.\n4. Manter atualizações:
         *   Mantenha o sistema e suas dependências atualizadas.\n5. Implementar controles
         *   de acesso: Use permissões baseadas em função para limitar acesso.\n\nEstas
         *   práticas ajudam a prevenir invasões e garantir a segurança dos sistemas."
         * }
         */
    }

    @PostMapping("/ask-agent")
    public String askAgent(@RequestBody AskRequest request) {
        return aiService.askAgentMulTools(request.question());
    }
}