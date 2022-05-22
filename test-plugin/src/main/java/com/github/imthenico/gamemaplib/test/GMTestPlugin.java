package com.github.imthenico.gamemaplib.test;

import com.github.imthenico.gamemaplib.test.arena.MatchManager;
import com.github.imthenico.gamemaplib.test.command.ModelEditorCommand;
import com.github.imthenico.gamemaplib.test.command.MatchCommand;
import com.github.imthenico.gamemaplib.test.listener.PlayerListener;
import com.github.imthenico.gamemaplib.test.map.ArenaModelData;
import com.github.imthenico.gamemaplib.test.map.GMTestModule;
import com.github.imthenico.gmlib.handler.HandlerRegistry;
import com.github.imthenico.gmlib.GameMapHandler;
import com.github.imthenico.gmlib.MapModel;
import com.github.imthenico.gmlib.Serialized;
import com.github.imthenico.gmlib.exception.NoWorldFoundException;
import com.github.imthenico.gmlib.pool.TemplatePool;
import com.github.imthenico.gmlib.swm.SWMWorldLoader;
import com.github.imthenico.gmlib.world.TemplatePack;
import com.google.gson.*;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class GMTestPlugin extends JavaPlugin {

    private ArenaModelData arenaModelData;
    private MapModel<ArenaModelData> mapModel;
    private GameMapHandler mapHandler;
    private TemplatePool templatePool;

    @Override
    public void onEnable() {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        HandlerRegistry handlerRegistry = HandlerRegistry.builder()
                .consume(new GMTestModule(gson))
                .build();

        templatePool = TemplatePool.create(new SWMWorldLoader());
        mapHandler = GameMapHandler.create(templatePool, handlerRegistry, gson);

        MatchManager matchManager = new MatchManager(this, mapHandler);
        File arenaFile = getDataFile(false);

        if (!arenaFile.exists()) {
            mapModel = createModel();
        } else {
            mapModel = deserializeModel(arenaFile);

            if (mapModel == null) mapModel = createModel();
        }

        arenaModelData = mapModel.getData();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(matchManager), this);

        CommandManager commandManager = new BukkitCommandManager("gmtest");
        PartInjector partInjector = PartInjector.create();

        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

        AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(partInjector);

        commandManager.registerCommands(builder.fromClass(new MatchCommand(matchManager, this)));
        commandManager.registerCommands(builder.fromClass(new ModelEditorCommand(arenaModelData, this)));
    }

    private MapModel<ArenaModelData> deserializeModel(File arenaFile) {
        try {
            JsonElement element = new JsonParser().parse(new FileReader(arenaFile));

            if (!element.isJsonObject()) {
                return null;
            }

            return mapHandler.fromJson(element.getAsJsonObject(), ArenaModelData.class, worldRequest -> {});
        } catch (NoWorldFoundException e) {
            setEnabled(false);
            throw new RuntimeException(e);
        } catch (FileNotFoundException ignored) {
            return null;
        }
    }

    private MapModel<ArenaModelData> createModel() {
        try {
            TemplatePack templatePack = TemplatePack.fromPool(
                    templatePool,
                    "1v1-test-map",
                    Collections.emptyList(),
                    (e) -> {}
            ).get();

            return MapModel.of("1v1-model", new ArenaModelData(), templatePack);
        } catch (InterruptedException | ExecutionException e) {
            Bukkit.getPluginManager().disablePlugin(this);
            throw new RuntimeException(e);
        }
    }

    public GameMapHandler getMapHandler() {
        return mapHandler;
    }

    public MapModel<ArenaModelData> getMapModel() {
        return mapModel;
    }

    public ArenaModelData getArenaModelData() {
        return arenaModelData;
    }

    public void saveModel() {
        Serialized serialized = mapHandler.serialize(mapModel);

        try (FileWriter fileWriter = new FileWriter(getDataFile(true))) {
            fileWriter.write(serialized.getJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getDataFile(boolean createIfNotExists) {
        File folder = getDataFolder();

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(getDataFolder(), "arenadata.json");

        if (!file.exists() && createIfNotExists) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return file;
    }
}