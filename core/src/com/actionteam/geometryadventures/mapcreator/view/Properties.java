package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.actionteam.geometryadventures.mapcreator.model.CollectibleTile;
import com.actionteam.geometryadventures.mapcreator.model.EnemyTile;
import com.actionteam.geometryadventures.mapcreator.model.LightTile;
import com.actionteam.geometryadventures.mapcreator.model.Map;
import com.actionteam.geometryadventures.mapcreator.model.PlayerTile;
import com.actionteam.geometryadventures.mapcreator.model.PortalTile;
import com.actionteam.geometryadventures.mapcreator.model.Tile;
import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.actionteam.geometryadventures.mapcreator.model.WeaponTypes;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by theartful on 5/4/18.
 */

public class Properties extends Table {

    private Tile tile;
    private Resources resources;
    public boolean pickTile = false;
    private TextField healthField;
    private TextField rField;
    private TextField gField;
    private TextField bField;
    private TextField intensityField;
    private TextField innerField;
    private TextField outerField;
    private TextButton pickTileBtn;
    private ButtonGroup buttonGroup;
    private SelectBox<String> weaponSelect;
    private int portalX = -1;
    private int portalY = -1;
    private Table table;
    public float x, y;
    public float barHeight = 30;
    private ScrollPane sp;
    private Table bar;
    private Label closeButton;
    private Label paddingLabel;
    private Map map;
    private EventListener typeListener = new EventListener() {
        @Override
        public boolean handle(Event event) {
            if(event.toString().equals("keyTyped")) {
                apply();
                return true;
            }
            return false;
        }
    };

    Properties(final Resources resources) {
        table = new Table();
        bar = new Table();
        bar.setBackground(new BackgroundColor(Color.rgba8888(0.5f,0.5f,0.5f,1f)));
        sp = new MyScrollPane(table, resources.skin);
        align(Align.top);
        add(bar).growX().height(barHeight);
        row();
        add(sp).grow();
        setBackground(new BackgroundColor(Color.rgba8888(1,1,1,0.3f)));
        this.resources = resources;

        closeButton = new Label("×", resources.skin);
        closeButton.setFontScale(1.5f);
        bar.align(Align.right);
        bar.add(new Label("", resources.skin)).growX();
        bar.add(closeButton);

        healthField = new TextField("", resources.skin);
        rField = new TextField("", resources.skin);
        gField = new TextField("", resources.skin);
        bField = new TextField("", resources.skin);
        intensityField = new TextField("", resources.skin);
        innerField = new TextField("", resources.skin);
        outerField = new TextField("", resources.skin);
        pickTileBtn = new TextButton("Pick Tile", resources.skin, "toggle");
        pickTileBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pickTile = !pickTile;
            }
        });
        buttonGroup = new ButtonGroup(pickTileBtn);
        buttonGroup.setMinCheckCount(0);
        buttonGroup.setMaxCheckCount(1);
        weaponSelect = new SelectBox<String>(resources.skin);
        weaponSelect.setItems((String[]) WeaponTypes.types.toArray());

        setTile(null, 0, 0);

        bar.addListener(new ClickListener() {
            Vector2 initial = new Vector2();
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean val = super.touchDown(event, x, y, pointer, button);
                initial = closeButton.localToStageCoordinates(new Vector2(x, y));
                return val;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                super.touchDragged(event, x, y, pointer);
                Vector2 newPos = closeButton.localToStageCoordinates(new Vector2(x, y));
                int r = incrementPosition(newPos.x - initial.x, initial.y - newPos.y);
                update(resources.screenWidth, resources.screenHeight);
                if(r == 1 || r == 3)
                    initial.set(newPos.x, initial.y);
                if(r == 2 || r == 3)
                    initial.set(initial.x, newPos.y);
            }
        });

        closeButton.addListener(new ClickListener(){
           @Override
           public void clicked (InputEvent event, float x, float y) {
               setVisible(false);
               buttonGroup.uncheckAll();
           }
        });
        paddingLabel = new Label(" ", resources.skin);
        paddingLabel.setHeight(100);
        x = 0;
        y = 100;

        rField.addListener(typeListener);
        gField.addListener(typeListener);
        bField.addListener(typeListener);
        intensityField.addListener(typeListener);
        innerField.addListener(typeListener);
        outerField.addListener(typeListener);
        healthField.addListener(typeListener);
    }

    private int incrementPosition(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        int result = 0;
        if(x < 0)
            x = 0;
        else if(x > resources.getUiWidth() - resources.getSideBarWidth() - getWidth())
            x = resources.getUiWidth() - resources.getSideBarWidth() - getWidth();
        else
            result += 1;
        if(y < 0) y = 0;
        else if (y >  resources.getUiHeight() - getHeight())
            y = resources.getUiHeight() - getHeight();
        else
            result += 2;
        return result;
    }

    private void apply() {
        if(this.tile == null) {
            try {
                float r = Float.parseFloat(rField.getText());
                float g = Float.parseFloat(gField.getText());
                float b = Float.parseFloat(bField.getText());
                float intensity = Float.parseFloat(intensityField.getText());
                if(r > 1) r = 1;
                if(g > 1) g = 1;
                if(b > 1) b = 1;
                if(r < 0) r = 0;
                if(g < 0) g = 0;
                if(b < 0) b = 0;
                if(intensity < 0) intensity = 0;
                resources.map.getConfig().ambientLight.set(r, g, b);
                resources.map.getConfig().ambientIntensity = intensity;
                resources.map.newLight = true;
            }
            catch(Exception e) {e.printStackTrace();}
        }
        else if (this.tile.tileType.equals(TileType.PORTAL)) {
            ((PortalTile) tile).toX = portalX;
            ((PortalTile) tile).toY = portalY;
        } else if (this.tile.tileType.equals(TileType.PLAYER)) {
            String weapon = weaponSelect.getSelected();
            int health = ((PlayerTile) tile).health;
            try {
                health = Integer.parseInt(healthField.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((PlayerTile) tile).weaponType = weapon;
            ((PlayerTile) tile).health = health;
        } else if (this.tile.tileType.equals(TileType.ENEMY)) {
            String weapon = weaponSelect.getSelected();
            int health = ((EnemyTile) tile).health;
            try {
                health = Integer.parseInt(healthField.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((EnemyTile) tile).subtype = weapon;
            ((EnemyTile) tile).health = health;
        } else if (this.tile.tileType.equals(TileType.LIGHT)) {
            try {
                float r = Float.parseFloat(rField.getText());
                float g = Float.parseFloat(gField.getText());
                float b = Float.parseFloat(bField.getText());
                float intensity = Float.parseFloat(intensityField.getText());
                float innerRadius = Float.parseFloat(innerField.getText());
                float outerRadius = Float.parseFloat(outerField.getText());
                if(r > 1) r = 1;
                if(g > 1) g = 1;
                if(b > 1) b = 1;
                if(r < 0) r = 0;
                if(g < 0) g = 0;
                if(b < 0) b = 0;
                if(innerRadius < 0) innerRadius = 0;
                if(outerRadius < 0) outerRadius = 0;
                if(intensity < 0) intensity = 0;
                ((LightTile)tile).lightColor.set(r, g, b);
                ((LightTile)tile).innerRadius = innerRadius;
                ((LightTile)tile).outerRadius = outerRadius;
                ((LightTile)tile).lightIntensity = intensity;
                resources.map.newLight = true;
            }
            catch(Exception e) {e.printStackTrace();}
        }
    }

    public void setTile(Tile tile, int x, int y) {
        table.clearChildren();
        if (pickTile) {
            portalX = x;
            portalY = y;
            pickTile = false;
            buttonGroup.uncheckAll();
            apply();
        } else {
            this.tile = tile;
            portalX = -1;
            portalY = -1;
        }
        if (this.tile == null) {
            setNullLayout();
        } else {
            addTileLayout();

            if (this.tile.tileType.equals(TileType.PORTAL)) {
                addPortalLayout();
            } else if (this.tile.tileType.equals(TileType.PLAYER)) {
                addPlayerLayout();
            } else if (this.tile.tileType.equals(TileType.ENEMY)) {
                addEnemyLayout();
            } else if (this.tile.tileType.equals(TileType.LIGHT)) {
                addLightLayout();
            } else if (this.tile.tileType.equals(TileType.COLLECTIBLE)) {
                addCollectibleLayout();
            }
        }
        table.row();
    }

    private void addCollectibleLayout() {
        table.add(new Label("Collectible type: ", resources.skin));
        table.add(new Label(((CollectibleTile)tile).subtype, resources.skin));
        table.row();
    }

    private void addLightLayout() {
        LightTile lt = (LightTile) tile;
        table.clearChildren();
        table.add(new Label("Point light", resources.skin)).colspan(2);
        table.row();
        table.add(new Label("Red: ", resources.skin));
        table.add(rField);
        table.row();
        table.add(new Label("Green: ", resources.skin));
        table.add(gField);
        table.row();
        table.add(new Label("Blue: ", resources.skin));
        table.add(bField);
        table.row();
        table.add(new Label("Intensity: ", resources.skin));
        table.add(intensityField);
        table.row();
        table.add(new Label("Inner radius: ", resources.skin));
        table.add(innerField);
        table.row();
        table.add(new Label("Outer radius: ", resources.skin));
        table.add(outerField);
        table.row();
        rField.setText(lt.lightColor.x + "");
        gField.setText(lt.lightColor.y + "");
        bField.setText(lt.lightColor.z + "");
        innerField.setText(lt.innerRadius + "");
        outerField.setText(lt.outerRadius + "");
        intensityField.setText(lt.lightIntensity + "");
    }

    private void addTileLayout() {
        table.top();
        table.add(new Label("Tile type: ", resources.skin));
        table.add(new Label(tile.tileType, resources.skin));
        table.row();
        table.add(new Label("Collidable: ", resources.skin));
        table.add(new Label(String.valueOf(tile.collidable), resources.skin));
        table.row();
        table.add(new Label("x: " + tile.x, resources.skin));
        table.add(new Label("y : " + tile.y, resources.skin));
        table.row();
        table.add(new Label("Animated: ", resources.skin));
        table.add(new Label(String.valueOf(tile.isAnimated), resources.skin));
        table.row();
        if (tile.isAnimated) {
            table.add(new Label("Frames: ", resources.skin));
            table.add(new Label(String.valueOf(tile.frames), resources.skin));
            table.row();
        }
    }

    private void addPlayerLayout() {
        table.add(new Label("Weapon: ", resources.skin));
        table.add(weaponSelect);
        table.row();
        table.add(new Label("Health: ", resources.skin));
        table.add(healthField);
        table.row();
        weaponSelect.setSelected(((PlayerTile) tile).weaponType);
        healthField.setText(String.valueOf(((PlayerTile) tile).health));
    }

    private void addEnemyLayout() {
        table.add(new Label("Subtype: ", resources.skin));
        table.add(weaponSelect);
        table.row();
        table.add(new Label("Health: ", resources.skin));
        table.add(healthField);
        table.row();

        healthField.setText(String.valueOf(((EnemyTile) tile).health));
        weaponSelect.setSelected(((EnemyTile) tile).subtype);
    }

    private void setNullLayout() {
        table.clearChildren();
        table.add(new Label("Ambient light", resources.skin)).colspan(2);
        table.row();
        table.add(new Label("Red: ", resources.skin));
        table.add(rField);
        table.row();
        table.add(new Label("Green: ", resources.skin));
        table.add(gField);
        table.row();
        table.add(new Label("Blue: ", resources.skin));
        table.add(bField);
        table.row();
        table.add(new Label("Intensity: ", resources.skin));
        table.add(intensityField);
        table.row();
        rField.setText(resources.map.getConfig().ambientLight.x + "");
        gField.setText(resources.map.getConfig().ambientLight.y + "");
        bField.setText(resources.map.getConfig().ambientLight.z + "");
        intensityField.setText(resources.map.getConfig().ambientIntensity + "");
    }

    private void addPortalLayout() {
        if (portalX == -1) {
            portalX = ((PortalTile) tile).toX;
            portalY = ((PortalTile) tile).toY;
        }
        table.add(new Label("x: " + portalX, resources.skin));
        table.add(new Label("y: " + portalY, resources.skin));
        table.row();
        table.add(pickTileBtn).colspan(2);
        table.row();
    }

    void update(int width, int height) {
        setWidth(resources.getSideBarWidth());
        setHeight(500);
        setPosition(x, resources.getUiHeight() - getHeight() - y);
    }
}
