package com.actionteam.geometryadventures.mapcreator.controller;

import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.actionteam.geometryadventures.mapcreator.view.TextureBox;
import com.actionteam.geometryadventures.mapcreator.view.TextureBoxListener;

/**
 * Created by theartful on 4/4/18.
 */

public class TextureBoxController implements TextureBoxListener {

    private TextureBox textureBox;
    private int textureIndex;
    private TileType type;
    private Controller controller;

    TextureBoxController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void textureChosen(TextureBox textureBox, TileType type, int textureIndex) {
        if (this.textureBox == textureBox) {
            if (this.textureIndex == TextureBox.NOTHING && type.isPattern) {
                textureBox.setSelectedTile(TextureBox.ALL);
                this.textureIndex = TextureBox.ALL;
            } else if (this.textureIndex == textureIndex) {
                textureBox.setSelectedTile(TextureBox.NOTHING);
                this.textureIndex = TextureBox.NOTHING;
            } else {
                textureBox.setSelectedTile(textureIndex);
                this.textureIndex = textureIndex;
                this.type = type;
            }
        } else {
            if (this.textureBox != null)
                this.textureBox.setSelectedTile(TextureBox.NOTHING);
            this.textureBox = textureBox;
            if (type.isPattern)
                this.textureIndex = TextureBox.ALL;
            else
                this.textureIndex = textureIndex;
            this.textureBox.setSelectedTile(this.textureIndex);
            this.type = type;
        }
        controller.fireEvent(MyEvents.TEXTURE_CHOSEN, new Object[]{this.type, this.textureIndex});
    }

    public void handle(int eventCode, Object message) {
    }
}