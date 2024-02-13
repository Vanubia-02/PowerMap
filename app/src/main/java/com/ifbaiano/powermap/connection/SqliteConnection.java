package com.ifbaiano.powermap.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteConnection extends SQLiteOpenHelper {

    private final String TABLE_USERS = "CREATE TABLE IF NOT EXISTS `powermap`.`users` (\n" +
            "  `id` INT NOT NULL,\n" +
            "  `email` VARCHAR(100) NOT NULL,\n" +
            "  `name` VARCHAR(50) NOT NULL,\n" +
            "  `password` VARCHAR(150) NOT NULL,\n" +
            "  `imgPath` VARCHAR(250) NOT NULL,\n" +
            "  `isAdmin` TINYINT NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";

    private final String TABLE_SCHEDULES = "CREATE TABLE IF NOT EXISTS `powermap`.`schedules` (\n" +
            "  `id` INT NOT NULL,\n" +
            "  `date` DATE NOT NULL,\n" +
            "  `dayOfWeek` INT NOT NULL,\n" +
            "  `description` TEXT NOT NULL,\n" +
            "  `repetition` INT NOT NULL,\n" +
            "  `users_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`, `users_id`),\n" +
            "  INDEX `fk_schedules_users1_idx` (`users_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_schedules_users1`\n" +
            "    FOREIGN KEY (`users_id`)\n" +
            "    REFERENCES `users` (`id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE NO ACTION);";

    private final String TABLE_CARS = "CREATE TABLE IF NOT EXISTS `powermap`.`cars` (\n" +
            "  `id` INT NOT NULL,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `users_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`, `users_id`),\n" +
            "  INDEX `fk_cars_users_idx` (`users_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_cars_users`\n" +
            "    FOREIGN KEY (`users_id`)\n" +
            "    REFERENCES  `users` (`id`)\n" +
            "    ON DELETE  CASCADE\n" +
            "    ON UPDATE NO ACTION);";

    private final String TABLE_CAR_MODELS = "CREATE TABLE IF NOT EXISTS `powermap`.`car_models` (\n" +
            "  `id` INT NOT NULL,\n" +
            "  `name` VARCHAR(100) NOT NULL,\n" +
            "  `year` INT NOT NULL,\n" +
            "  `pathImg` VARCHAR(250) NOT NULL,\n" +
            "  `energyConsumption` FLOAT NOT NULL,\n" +
            "  `fuelConsumption` FLOAT NULL DEFAULT NULL,\n" +
            "  `cars_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`, `cars_id`),\n" +
            "  INDEX `fk_car_models_cars1_idx` (`cars_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_car_models_cars1`\n" +
            "    FOREIGN KEY (`cars_id`)\n" +
            "    REFERENCES `cars` (`id`)\n" +
            "    ON DELETE  CASCADE\n" +
            "    ON UPDATE NO ACTION);";

    public SqliteConnection(@Nullable Context context) {
        super(context, "powermap.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.TABLE_USERS + this.TABLE_SCHEDULES + this.TABLE_CARS + this.TABLE_CAR_MODELS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}