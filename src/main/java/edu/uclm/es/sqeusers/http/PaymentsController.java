package edu.uclm.es.sqeusers.http;

import java.util.HashMap;
import java.util.Map;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
@RequestMapping("payments")
@CrossOrigin("*")
public class PaymentsController {

    static {
        Stripe.apiKey = "sk_test_51P1sD3RsjKhkWbXKleiFjeTdx769qWaOJbASeBvpmYe7ONlfO0aq1KRKw6sTAcdvKtv6ozwnSH4lLA7cdhfBYikN00wC642Brf";
    }

    @GetMapping("/prepagar")
    public Map<String,String> prepagar(@RequestParam double importe) {
        long total = (long) (importe*100);
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setCurrency("eur")
            .setAmount((long) (importe * 100))
            .build();

        try {
            PaymentIntent intent = PaymentIntent.create(params);
            JSONObject jso = new JSONObject(intent.toJson());
            String clientSecret = jso.getString("client_secret");
            Map<String, String> result = new HashMap<>();
            result.put("clientSecret", clientSecret);
            return result;

        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
        
        
    }
    
}
