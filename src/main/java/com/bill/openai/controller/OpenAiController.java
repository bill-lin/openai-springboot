package com.bill.openai.controller;

import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.billing.CreditGrantsResponse;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.entity.common.DeleteResponse;
import com.unfbx.chatgpt.entity.completions.Completion;
import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import com.unfbx.chatgpt.entity.edits.Edit;
import com.unfbx.chatgpt.entity.edits.EditResponse;
import com.unfbx.chatgpt.entity.embeddings.Embedding;
import com.unfbx.chatgpt.entity.embeddings.EmbeddingResponse;
import com.unfbx.chatgpt.entity.files.File;
import com.unfbx.chatgpt.entity.files.UploadFileResponse;
import com.unfbx.chatgpt.entity.fineTune.Event;
import com.unfbx.chatgpt.entity.fineTune.FineTune;
import com.unfbx.chatgpt.entity.fineTune.FineTuneResponse;
import com.unfbx.chatgpt.entity.images.*;
import com.unfbx.chatgpt.entity.models.Model;
import com.unfbx.chatgpt.entity.moderations.Moderation;
import com.unfbx.chatgpt.entity.moderations.ModerationResponse;
import com.unfbx.chatgpt.entity.whisper.Whisper;
import com.unfbx.chatgpt.entity.whisper.WhisperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/openai")
public class OpenAiController {

    //TODO
//    https://chat.openai.com/backend-api/conversation

    private final OpenAiClient openAiApi;

    @Autowired
    public OpenAiController(OpenAiClient openAiApi) {
        this.openAiApi = openAiApi;
    }

    @GetMapping("/models")
    public List<Model> models() {
        return openAiApi.models();
    }

    @GetMapping("/model/{id}")
    public Model model(@PathVariable String id) {
        return openAiApi.model(id);
    }

    @PostMapping("/completions")
    public CompletionResponse completions(@RequestBody Completion completion) {
        return openAiApi.completions(completion);
    }

    @GetMapping("/completions")
    public CompletionResponse completions(@RequestParam String question) {
        return openAiApi.completions(question);
    }

    @PostMapping("/edits")
    public EditResponse edit(@RequestBody Edit edit) {
        return openAiApi.edit(edit);
    }


    @PostMapping("/images")
    public ImageResponse genImages(@RequestParam String prompt) {
        return openAiApi.genImages(prompt);
    }

    @PostMapping("/images/advanced")
    public ImageResponse genImages(@RequestBody Image image) {
        return openAiApi.genImages(image);
    }

    // ... other endpoints ...

    @PostMapping("/files")
    public UploadFileResponse uploadFile(@RequestParam String purpose, @RequestParam("file") String file) {
        return openAiApi.uploadFile(purpose, new java.io.File(file));
    }

    @PostMapping("/files/simple")
    public UploadFileResponse uploadFile(@RequestParam("file") String file) {
        return openAiApi.uploadFile(new java.io.File(file));
    }


    @PostMapping("/edit-images")
    public List<Item> editImages(@RequestParam("image") String image, @RequestParam String prompt) {
        return openAiApi.editImages(new java.io.File(image), prompt);
    }

    @PostMapping("/edit-images/advanced")
    public List<Item> editImages(@RequestParam("image") String image, @RequestBody ImageEdit imageEdit) {
        return openAiApi.editImages(new java.io.File(image), imageEdit);
    }

    @PostMapping("/edit-images/masked")
    public List<Item> editImages(@RequestParam("image") String image, @RequestParam("mask") String mask,
                                 @RequestBody ImageEdit imageEdit) {
        return openAiApi.editImages(new java.io.File(image), new java.io.File(mask), imageEdit);
    }

    @PostMapping("/variations-images")
    public ImageResponse variationsImages(@RequestParam("image") String image,
                                          @RequestBody ImageVariations imageVariations) {
        return openAiApi.variationsImages(new java.io.File(image), imageVariations);
    }

    // ... other endpoints ...

    @PostMapping("/speech-to-text-transcriptions")
    public WhisperResponse speechToTextTranscriptions(@RequestParam("file") String file,
                                                      @RequestParam(required = false) Whisper.Model model) {
        return openAiApi.speechToTextTranscriptions(new java.io.File(file), model);
    }

    @PostMapping("/speech-to-text-translations")
    public WhisperResponse speechToTextTranslations(@RequestParam("file") String file,
                                                    @RequestParam(required = false) Whisper.Model model) {
        return openAiApi.speechToTextTranslations(new java.io.File(file), model);
    }


    @PostMapping("/embeddings")
    public EmbeddingResponse embeddings(@RequestParam String input) {
        return openAiApi.embeddings(input);
    }

    @PostMapping("/embeddings/advanced")
    public EmbeddingResponse embeddings(@RequestBody Embedding embedding) {
        return openAiApi.embeddings(embedding);
    }

    @GetMapping("/files")
    public List<File> files() {
        return openAiApi.files();
    }

    @DeleteMapping("/files/{fileId}")
    public DeleteResponse deleteFile(@PathVariable String fileId) {
        return openAiApi.deleteFile(fileId);
    }

    @GetMapping("/files/{fileId}")
    public File retrieveFile(@PathVariable String fileId) {
        return openAiApi.retrieveFile(fileId);
    }

    // ... other endpoints ...

    @PostMapping("/fine-tunes")
    public FineTuneResponse fineTune(@RequestBody FineTune fineTune) {
        return openAiApi.fineTune(fineTune);
    }

    @PostMapping("/fine-tunes/simple")
    public FineTuneResponse fineTune(@RequestParam String trainingFileId) {
        return openAiApi.fineTune(trainingFileId);
    }

    @GetMapping("/fine-tunes")
    public List<FineTuneResponse> fineTunes() {
        return openAiApi.fineTunes();
    }

    @GetMapping("/fine-tunes/{fineTuneId}")
    public FineTuneResponse retrieveFineTune(@PathVariable String fineTuneId) {
        return openAiApi.retrieveFineTune(fineTuneId);
    }

    @PostMapping("/fine-tunes/{fineTuneId}/cancel")
    public FineTuneResponse cancelFineTune(@PathVariable String fineTuneId) {
        return openAiApi.cancelFineTune(fineTuneId);
    }

    @GetMapping("/fine-tunes/{fineTuneId}/events")
    public List<Event> fineTuneEvents(@PathVariable String fineTuneId) {
        return openAiApi.fineTuneEvents(fineTuneId);
    }

    @DeleteMapping("/fine-tunes/{model}")
    public DeleteResponse deleteFineTuneModel(@PathVariable String model) {
        return openAiApi.deleteFineTuneModel(model);
    }

    @PostMapping("/chat-completion")
    public ChatCompletionResponse chatCompletion(@RequestBody ChatCompletion chatCompletion) {
        return openAiApi.chatCompletion(chatCompletion);
    }

    @PostMapping("/chat-completion/simple")
    public ChatCompletionResponse chatCompletion(@RequestBody List<Message> messages) {
        return openAiApi.chatCompletion(messages);
    }

    @PostMapping("/moderations")
    public ModerationResponse moderations(@RequestParam String input) {
        return openAiApi.moderations(input);
    }

    @PostMapping("/moderations/advanced")
    public ModerationResponse moderations(@RequestBody Moderation moderation) {
        return openAiApi.moderations(moderation);
    }

    @GetMapping("/credit-grants")
    public CreditGrantsResponse creditGrants() {
        return openAiApi.creditGrants();
    }
}
