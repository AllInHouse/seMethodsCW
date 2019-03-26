package springExample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private long counter = 0;
    private static final String template = "Hello, %s!";

    @RequestMapping
    public Greeting greeting(@RequestParam(value="name", defaultValue="") String name) {
        return new Greeting(counter++, String.format(template, name));
    }


    public class Greeting{
        private final long id;
        private final String content;

        public Greeting(long id, String content)
        {
            this.id = id;
            this.content = content;
        }

        public long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }
    }
}
