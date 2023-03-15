## Spring Boot & ChatGPT & Facebook Messenger Integration

> This is a sample Spring Boot project that integrates ChatGPT with Facebook Messenger using the Messenger Platform
> APIs.

####

#### Prerequisites

To run this project, you'll need the following:

- Java 11 or higher
- Gradle 6.7 or higher
- A Facebook account and a Facebook page

#### Setup

1. Create a Facebook page and a Facebook app. Follow
   the [Facebook Messenger Platform Getting Started](https://developers.facebook.com/docs/messenger-platform/get-started)
   guide for detailed
   instructions.
2. Clone the repository and open it in your favorite IDE.
3. Update the following properties in the application.yml file with your Facebook app credentials:

- Facebook:

```yml
facebook:
  verify-token: <your-verify-token>
  access-token: <your-page-token>
```

- OpenAI:

```yml
openai:
  apiKey: <your-openai-api-key>
  gpt-model: gpt-3.5-turbo
  audio-model: whisper-1
  http-client:
    read-timeout: 3000
    connect-timeout: 3000
  urls:
    base-url: https://api.openai.com/v1
    chat-url: /chat/completions
    create-transcription-url: /audio/transcriptions
```

4. Build the project using Gradle:

```bash
./gradlew build
```

5. Run the project using Gradle:

```bash
./gradlew bootRun
```

6. Register the webhook URL with [Facebook Messenger](https://developers.facebook.com/docs/messenger-platform/webhooks).
   Follow the Facebook Messenger Webhooks guide for detailed
   instructions.
7. Publish the app to a public URL. You can use [ngrok](https://ngrok.com/) for this purpose.
8. Test the integration by sending a message to your Facebook page.

### Other Resources

- [How to get page access token](https://github.com/Boidushya/FrameBot/blob/master/generateToken.md)
- [Integrating OpenAI with Springboot](https://github.com/wenqiglantz/chatgpt-whisper-spring-boot)
- [RestFB](https://restfb.com/documentation/)

