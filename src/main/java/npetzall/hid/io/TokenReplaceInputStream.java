package npetzall.hid.io;

import npetzall.hid.api.exchange.HIDExchangeContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nosse on 2015-11-11.
 */
public class TokenReplaceInputStream extends InputStream {

    private final static String PRE_FIX = "${";
    private final static String POST_FIX = "}";

    private final InputStream sourceInputStream;
    private final HIDExchangeContext exchangeContext;
    private final int longestTokenLength;

    public TokenReplaceInputStream(InputStream sourceInputStream, HIDExchangeContext hidExchangeContext) {
        this.sourceInputStream = sourceInputStream;
        this.exchangeContext = hidExchangeContext;
        int longest = 0;
        for(String str : exchangeContext.getAttributeValues()) {
            longest = longest < str.length() ? str.length(): longest;
        }
        longestTokenLength = longest+3;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

}
