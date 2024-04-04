package github.io.robinhosz.springai.application.controllers;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/v1/ai-assistant")
public class AIAssistantController {

    private final OpenAiChatClient openAiChatClient;

    public AIAssistantController(OpenAiChatClient openAiChatClient) {
        this.openAiChatClient = openAiChatClient;
    }

    //EXAMPLE WITH STRING
    @GetMapping("/info")
    public String info(@RequestParam(value = "message") String message) {
        return openAiChatClient.call(message);

    }


    //EXAMPLE WITH PROMPT

    @GetMapping("/info/prompt")
    public ChatResponse infoPrompt(@RequestParam(value = "message") String message) {
        return openAiChatClient.call(new Prompt(message));
    }

    //EXAMPLE WITH PROMPT TEMPLATE
    @GetMapping("/info/prompt/player")
    public String infoPromptReviews(@RequestParam(value = "player") String player) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                Me forneça informações sobre o jogador de e-sports {player}. E também faça uma biografia dele.
                """);

        promptTemplate.add("player", player);

        return this.openAiChatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

    //EXAMPLE WITH WEB FLUX STEAMS
    @GetMapping("/stream/info")
    public Flux<String> infoStream(@RequestParam(value = "message") String message) {
        return openAiChatClient.stream(message);
    }
}
