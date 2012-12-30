/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import Utilities.ImageCollection;
import Utilities.SoundFile;
import Utilities.Vector2;
import Utilities.ViewScreen;
import java.util.ArrayList;
import java.util.LinkedList;
import particlefx.ParticleManager;
import world.Level;

/**
 *
 * @author Peter
 */
public class Player extends GameObject {

    public double health;
    int jumps;
    int canshoot = 0;
    int firing = 2;
    int currentweapon;
    boolean canJump;
    boolean canSwitch;
    boolean facing;
    Animation pistol;
    Animation pistolarm;
    Animation knuck;
    Animation knack;
    Animation shotgun;
    Animation ak;
    Animation head;
    Animation muzzleflare;
    Animation left;
    Animation right;
    Vector2 gunpos;
    boolean[] weapons;
    int[] ammo;
    SoundFile gunSound;

    public Player(Vector2 pos) {
        super(new Animation("legs", 10, "png"), pos);
        health = 100;
        gunpos = pos.clone();
        shotgun = new Animation("shotgun_temp", 2, "png");
        ak = new Animation("assaultrifle", 2, "png");
        pistol = new Animation("pover", 2, "png");
        pistolarm = new Animation("punder", 2, "png");
        knuck = new Animation("knuck", 4, "png");

        head = new Animation("head", 2, "png");
        muzzleflare = new Animation("muzzleflare", 2, "png");
        right = new Animation("legs", 10, "png");
        left = new Animation("lag", 10, "png");
        currentweapon = 2;
        canshoot = 0;
        weapons = new boolean[]{true,true,true,true};
        ammo = new int[]{1,40,8,40};
        facing = true;

    }

    @Override
    public void create() {
        jumps = 0;
        canJump = false;
    }

    public void switchUp() {
        if (canSwitch) {
            canSwitch = false;
            currentweapon++;
            if (currentweapon > weapons.length - 1) {
                currentweapon = 0;
            }
            while (!weapons[currentweapon]) {
                currentweapon++;
                if (currentweapon > weapons.length - 1) {
                    currentweapon = 0;
                }
            }
        }
    }

    public void switchDown() {
        if (canSwitch) {
            canSwitch = false;
            currentweapon--;
            if (currentweapon < 0) {
                currentweapon = weapons.length - 1;
            }
            while (!weapons[currentweapon]) {
                currentweapon--;
                if (currentweapon < 0) {
                    currentweapon = weapons.length - 1;
                }
            }
        }
    }

    public void resetSwitch() {
        canSwitch = true;
    }

    @Override
    public void step(Level level, Player player, LinkedList<GameObject> objects) {
        ammo[0] = 1;
        health = Math.min(100, health + 0.05);
        if (firing < 10) {
            firing++;
        }
        canshoot = Math.max(0, canshoot - 1);
        if (level.collide(boundingBox)) {
            position.subtract(velocity);
            position.dX(velocity.getX());
            updateBoundingBox();
            if (level.collide(boundingBox)) {
                position.dX(-velocity.getX());
                velocity.setX(0);
            }
            position.dY(velocity.getY());
            updateBoundingBox();
            if (level.collide(boundingBox)) {
                position.dY(-velocity.getY());
                velocity.setY(0);
            }
        }
        position.dY(1);
        updateBoundingBox();
        if (level.collide(boundingBox)) {
            velocity.setY(0);
            jumps = 2;
        } else {
            velocity.dY(0.2);
            jumps = Math.min(jumps, 1);
        }
        position.dY(-1);
        right.update();
        left.update();
        if (velocity.getX() == 0) {
            right.index = 0;
            left.index = 8;
        } else {
            right.speed = (float) (velocity.getX() / 12);
            left.speed = (float) (velocity.getX() / 12);
        }

    }

    public void jump() {
        if (canJump == true) {
            if (jumps > 0) {
                canJump = false;
                velocity.setY(-6);
                jumps--;
                System.out.println(jumps);
            }
        }
    }

    public void resetJump() {
        canJump = true;
    }

    public void rotateHead(Vector2 mouse, ViewScreen viewscreen) {
        Vector2 m = mouse.clone();
        m.dX(-viewscreen.GetX());
        m.dY(-viewscreen.GetY());
        m.subtract(position);
        double dir = Math.atan2(m.getY(), m.getX());
        if (m.getX() > 0) {
            ak.index = 0;
            shotgun.index = 0;
            pistol.index = 0;
            pistolarm.index = 0;
            head.index = 0;
            facing = true;
            knuck = new Animation("knuck", 4, "png");
        } else {
            ak.index = 1;
            shotgun.index = 1;
            pistol.index = 1;
            pistolarm.index = 1;
            head.index = 1;
            facing = false;
            knuck = new Animation("knack", 4, "png");
        }
        gunpos = position.clone();
        gunpos.add(new Vector2(Math.cos(dir) * 16, Math.sin(dir) * 16));
        gunpos.dY(-3);
        pistol.rotate(dir);
        pistolarm.rotate(dir);
        shotgun.rotate(dir);
        ak.rotate(dir);
        knuck.rotate(dir);
        if (firing == 0) {
            muzzleflare.rotate(dir);
        }
        dir = Math.atan2(m.getY() + 16, m.getX());
        head.rotate(dir);

    }

    public void move(boolean left, boolean right) {
        if (left && !right) {
            velocity.setX(Math.max(velocity.getX() - 1, -4));
        } else if (right && !left) {
            velocity.setX(Math.min(velocity.getX() + 1, 4));
        } else if (velocity.getX() > 0) {
            velocity.setX(Math.max(velocity.getX() - 1, 0));
        } else {
            velocity.setX(Math.min(velocity.getX() + 1, 0));
        }
    }

    @Override
    public void draw(ImageCollection batch) {
        if (currentweapon == FISTS) {
            if (firing < 8) {
                knuck.index = firing / 2;
            } else {
                knuck.index = 0;
            }
            knuck.draw(batch, gunpos, 120);
        } else {
            if (firing < 2) {
                muzzleflare.index = firing;
                muzzleflare.draw(batch, gunpos, 120);
            }
            switch (currentweapon) {
                case SHOTGUN:
                    shotgun.draw(batch, gunpos, 120);
                    pistolarm.draw(batch, gunpos, 98);
                    break;
                case PISTOL:
                    pistol.draw(batch, gunpos, 120);
                    pistolarm.draw(batch, gunpos, 98);
                    break;
                case MACHINEGUN:
                    ak.draw(batch, gunpos, 120);
                    pistolarm.draw(batch, gunpos, 98);
            }
        }
        position.dY(-12);
        head.draw(batch, position, 110);
        position.dY(12);
        if (facing) {
            right.draw(batch, position, depth);
        } else {
            left.draw(batch, position, depth);
        }
    }

    public int currentAmmo() {
        return ammo[currentweapon];
    }
    public String getWeapon(){
        return WEAPONS[currentweapon];
    }

    public int[] getAmmo() {
        return ammo;
    }

    public void shoot(LinkedList<GameObject> objects, Vector2 mouse, ViewScreen viewscreen, ParticleManager shells) {
        if (canshoot == 0) {
            if (ammo[currentweapon] > 0) {
                ammo[currentweapon]--;
                Bullet b;
                firing = 0;
                Vector2 m = mouse.clone();
                m.dX(-viewscreen.GetX());
                m.dY(-viewscreen.GetY());
                m.subtract(position);
                double dir = Math.atan2(-m.getY(), m.getX());
                Vector2 pos = position.clone();
                pos.add(new Vector2(Math.cos(dir) * 24, -Math.sin(dir) * 24));
                pos.dY(-6);

                switch (currentweapon) {
                    case FISTS:
                        b = new Bullet(pos, dir, 10);
                        b.type = -1;
                        b.damage = 40;
                        objects.add(b);
                        canshoot = 60;
                        //gunSound = new SoundFile("Sounds/miss.wav", 1);
                        //gunSound.start();
                        break;
                    case PISTOL:
                        b = new Bullet(pos, dir + offset() * .1, 20);
                        b.damage = 20;
                        objects.add(b);
                        shells.addExplosion(gunpos, 1, 3);
                        canshoot = 20;
                        //gunSound = new SoundFile("Sounds/gun5.wav", 1);
                        //gunSound.start();
                        break;
                    case SHOTGUN:
                        canshoot = 45;
                        shells.addExplosion(gunpos, 1, 3);
                        for (int i = 0; i < 6; i++) {
                            objects.add(new Bullet(pos, dir + offset() * .3, 20));
                        }
                        //gunSound = new SoundFile("Sounds/gun6.wav", 1);
                        //gunSound.start();
                        break;
                    case MACHINEGUN:
                        shells.addExplosion(gunpos, 1, 4);
                        objects.add(new Bullet(pos, dir + offset() * .1, 20));
                        canshoot = 7;
                        //gunSound = new SoundFile("Sounds/gun7.wav", 1);
                        //gunSound.start();
                        break;
                }
            }
        }
    }

    public double offset() {
        return Math.random() - 0.5;
    }
    public static final int FISTS = 0;
    public static final int PISTOL = 1;
    public static final int SHOTGUN = 2;
    public static final int MACHINEGUN = 3;
    public static final String[] WEAPONS={"Brass Knuckles","Pistol","Shotgun","Assault Rifle"};
}
