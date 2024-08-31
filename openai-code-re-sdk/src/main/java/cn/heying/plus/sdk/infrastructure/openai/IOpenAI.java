package cn.heying.plus.sdk.infrastructure.openai;


import cn.heying.plus.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.heying.plus.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

public interface IOpenAI {

    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;

}
