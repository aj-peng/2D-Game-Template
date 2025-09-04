package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Key();
		gp.obj[0].worldX = gp.tileSize * 15;
		gp.obj[0].worldY = gp.tileSize * 15;
		
		gp.obj[1] = new OBJ_Key();
		gp.obj[1].worldX = gp.tileSize * 19;
		gp.obj[1].worldY = gp.tileSize * 23;
		
		gp.obj[2] = new OBJ_Door();
		gp.obj[2].worldX = gp.tileSize * 15;
		gp.obj[2].worldY = gp.tileSize * 18;
		
		gp.obj[3] = new OBJ_Door();
		gp.obj[3].worldX = gp.tileSize * 16;
		gp.obj[3].worldY = gp.tileSize * 35;
		
		gp.obj[5] = new OBJ_Chest();
		gp.obj[5].worldX = gp.tileSize * 19;
		gp.obj[5].worldY = gp.tileSize * 35;
	}
}
