# ShadowboxFX

Provides a shadowbox control for JavaFX applications.

To get the effect, wrap the node you want boxed out in a ShadowboxPane. Then, from
any node that is child of the ShadowboxPane, use this code to show a new control, overlayed
on the shadowbox.  
```
ShadowboxPane.find(node).showOverlayOnShadowbox(overlay_node); 
```

When the done, remove the overlay with:   

```
ShadowboxPane.find(node).removeOverlay(); 
```

## Example

Here is an example from [MuseIDE](http://ide4selenium.com) (an IDE for web test automation using Selenium), for which 
ShadowboxFx was initially implemented:

![Example Screenshot](https://github.com/ChrisLMerrill/shadowboxfx/raw/master/screenshot.png) 
