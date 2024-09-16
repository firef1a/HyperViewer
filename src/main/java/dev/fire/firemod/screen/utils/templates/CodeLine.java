package dev.fire.firemod.screen.utils.templates;

import net.minecraft.text.Text;

public class CodeLine {
    public Text text;
    public int indent;

    public CodeLine(Text text, int indent) {
        this.text = text;
        this.indent = indent;
    }
}
