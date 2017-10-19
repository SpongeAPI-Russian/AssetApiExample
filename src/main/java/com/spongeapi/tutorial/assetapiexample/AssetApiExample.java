package com.spongeapi.tutorial.assetapiexample;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Plugin(
        id = "assetapiexample",
        name = "AssetApiExample",
        version = "0.1-SNAPSHOT",
        description = "Краткое описание asset api",
        url = "https://spongeapi.com",
        authors = {
                "Xakep_SDK"
        }
)
public class AssetApiExample {

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path confDir;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        Optional<Asset> assetOpt = Sponge.getAssetManager().getAsset(this, "myfile.txt");
        assetOpt.ifPresent(asset -> {
            try {
                asset.copyToDirectory(confDir);
                logger.info("Файл распакован!");
            } catch (IOException e) {
                logger.error("Ошибка распаковки файла!", e);
            }
        });

        Optional<Asset> assetOpt1 = container.getAsset("bestdir/bestfile.txt");
        assetOpt1.ifPresent(asset -> {
            try {
                asset.copyToDirectory(confDir.resolve("bestdir"));
                logger.info("Файл распакован");
            } catch (IOException e) {
                logger.error("Ошибка распаковки файла!", e);
            }
        });
    }
}
