package com.wr.java.ai;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// 测试 Kokoro TTS 流式语音合成功能 不用管 字节码
public class KokoroTTSStreamTest {
    public static void main(String[] args) throws Exception {
        String url = "http://127.0.0.1:8880/v1/audio/speech";
        String json = """
        {
          "model": "kokoro",
          "input": "今天我们要测试 Kokoro TTS 的流式语音合成功能。这段文字会比之前的示例更长一些，用来模拟真实使用场景。在这段话中，每个音节都会被逐步生成，并且能够边生成边播放，从而实现实时听感。通过这样的测试，我们可以观察语音的流畅度、生成速度以及音质表现。同时，这段文字还包含了多种句式和标点，以保证语音合成能够正确处理停顿和语调变化。如果一切正常，你应该可以在播放过程中听到自然的停顿和语气变化，而不需要等到整段文字生成完。通过这种方式，我们能够有效验证 Kokoro TTS 流式功能的实际效果，并且为后续的应用场景提供参考。希望这段文字能够帮助你完成流式播放测试。",
          "voice": "zf_xiaoxiao",
          "response_format": "mp3",
          "download_format": "mp3",
          "speed": 1,
          "stream": true,
          "return_download_link": false,
          "lang_code": "zh",
          "normalization_options": {
            "normalize": true,
            "unit_normalization": false,
            "url_normalization": true,
            "email_normalization": true,
            "optional_pluralization_normalization": true,
            "phone_normalization": true
          }
        }
        """;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        try (InputStream is = response.body();
             FileOutputStream fos = new FileOutputStream("test.mp3")) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                System.out.println("Received " + len + " bytes");
            }
        }

        System.out.println("MP3 saved.");
    }
}
