package pack_technical;
import py4j.GatewayServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class SharedStackJ2P {

    private Stack stack;
    private GatewayServer gatewayServer;

    public SharedStackJ2P() {

        stack = new Stack();

    }

    public Stack getStack() {
        return stack;
    }






}