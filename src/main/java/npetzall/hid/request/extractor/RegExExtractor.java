package npetzall.hid.request.extractor;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.request.HIDDataExtractor;
import npetzall.hid.api.request.HIDRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExExtractor implements HIDDataExtractor {

    private final Pattern pattern;
    private final Map<String, Integer> groupCounter = new HashMap<>();

    public RegExExtractor(String regex) {
        pattern = Pattern.compile(regex);
        Matcher m = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>").matcher(regex);

        while (m.find()) {
            groupCounter.put(m.group(1), 1);
        }
        if (groupCounter.isEmpty()) {
            throw new IllegalStateException("RegEx didn't contain any groups");
        }
    }

    @Override
    public void extract(HIDRequest request, HIDExchangeContext exchangeContext) {
        Matcher matcher = pattern.matcher(request.getBodyString());
        String replacement;
        while(matcher.find()) {
            for(Entry<String, Integer> group :groupCounter.entrySet()) {
                replacement = matcher.group(group.getKey());
                if (replacement != null && !replacement.isEmpty()) {
                    exchangeContext.addAttribute(group.getKey()+"_"+group.getValue(), replacement);
                    group.setValue(group.getValue()+1);
                }
            }
        }
    }
}
