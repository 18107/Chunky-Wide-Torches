/* Copyright (c) 2016 Jesper Öqvist <jesper@llbit.se>
 *
 * Chunky is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Chunky is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with Chunky.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.id107.chunky.plugin;

import se.llbit.chunky.Plugin;
import se.llbit.chunky.main.Chunky;
import se.llbit.chunky.main.ChunkyOptions;
import se.llbit.chunky.model.TexturedBlockModel;
import se.llbit.chunky.model.TorchModel;
import se.llbit.chunky.renderer.scene.Scene;
import se.llbit.chunky.resources.Texture;
import se.llbit.chunky.ui.ChunkyFx;
import se.llbit.math.Ray;
import se.llbit.chunky.block.Block;

/**
 * This plugin makes torches render as glowstone blocks when tracing diffuse rays.
 */
public class BlockMod implements Plugin {
  @Override public void attach(Chunky chunky) {
    Block.set(Block.TORCH_ID, new Block(Block.TORCH_ID, "block:wide_torch", Texture.torch) {
      @Override public boolean intersect(Ray ray, Scene scene) {
    	  if (ray.specular) {
    		  return TorchModel.intersect(ray, Texture.torch);
    	  } else {
    		  return TexturedBlockModel.intersect(ray, Texture.glowstone);
    	  }
      }
    });
  }

  public static void main(String[] args) throws Exception {
    // Start Chunky normally with this plugin attached.
    Chunky.loadDefaultTextures();
    Chunky chunky = new Chunky(ChunkyOptions.getDefaults());
    new BlockMod().attach(chunky);
    ChunkyFx.startChunkyUI(chunky);
  }
}
