package com.codename1.samples;


import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Container;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BorderLayout;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class SafeAreasSample {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form hi = new Form("Hi World",new BorderLayout());
        Tabs tabs = new Tabs();
        
        Container tab1Contents = new Container();
        tab1Contents.setLayout(BoxLayout.y());
        tab1Contents.setScrollableY(true);
        tab1Contents.setSafeArea(true);
        String[] names = new String[]{"John", "Mary", "Joseph", "Solomon", "Jan", "Judy", "Patricia", "Ron", "Harry"};
        String[] positions = new String[]{"Wizard", "Judge", "Doctor"};
        int len = names.length;
        for (int i=0; i<len; i++) {
            MultiButton btn = new MultiButton(names[i]);
            btn.setTextLine2(positions[i % positions.length]);
            tab1Contents.add(btn);
        }
        
        Container tab2Contents = new Container();
        tab2Contents.setLayout(BoxLayout.y());
        tab2Contents.setScrollableY(true);
        
        for (int i=0; i<len; i++) {
            MultiButton btn = new MultiButton(names[i]);
            btn.setTextLine2(positions[i % positions.length]);
            tab2Contents.add(btn);
        }
        
        String description = "This Demo shows the use of safeArea to ensure that a container's children are not covered by the notch on iPhone X.  You should run this demo using the iPhone X skin or iPhone X device to see the difference.\n\n"
                + "The Safe tab uses setSafeArea(true) to ensure that the children are not affected by the notch.  The unsafe tab is not.  \n\n"
                + "You'll need to use landscape mode to see the difference because the notch will be on the left or right in that case.";
        SpanLabel spanLabel = new SpanLabel(description);
        spanLabel.setSafeArea(true);
        tabs.addTab("Description", spanLabel);
        tabs.addTab("Safe Tab", tab1Contents);
        tabs.addTab("Unsafe Tab", tab2Contents);
        
        hi.add(BorderLayout.CENTER, tabs);
        hi.show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

}
