/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.sensors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SensorTest {

    private Sensor        sensor;
    private SecurityAlarm securityAlarm;

    @BeforeEach
    public void setUp() {
        this.sensor        = new Sensor();
        this.securityAlarm = new SecurityAlarm("Cozinha");
        this.securityAlarm.register(this.sensor);
    }

    /* DisarmedSensor */

    @Test
    public void shouldArmDisarmedSensor() {
        this.sensor.setState(DisarmedSensor.getInstance());

        assertTrue(this.sensor.arm());
        assertEquals(ArmedSensor.getInstance(), this.sensor.getState());
        assertFalse(this.securityAlarm.isOn());
    }

    @Test
    public void shouldNotTriggerDisarmedSensor() {
        this.sensor.setState(DisarmedSensor.getInstance());

        assertFalse(this.sensor.trigger());
        assertEquals(DisarmedSensor.getInstance(), this.sensor.getState());
        assertFalse(this.securityAlarm.isOn());
    }

    @Test
    public void shouldNotResetDisarmedSensor() {
        this.sensor.setState(DisarmedSensor.getInstance());

        assertFalse(this.sensor.reset());
        assertEquals(DisarmedSensor.getInstance(), this.sensor.getState());
        assertFalse(this.securityAlarm.isOn());
    }

    @Test
    public void shouldNotDisarmDisarmedSensor() {
        this.sensor.setState(DisarmedSensor.getInstance());

        assertFalse(this.sensor.disarm());
        assertEquals(DisarmedSensor.getInstance(), this.sensor.getState());
        assertFalse(this.securityAlarm.isOn());
    }

    /* ArmedSensor */

    @Test
    public void shouldNotArmArmedSensor() {
        this.sensor.setState(ArmedSensor.getInstance());

        assertFalse(this.sensor.arm());
        assertEquals(ArmedSensor.getInstance(), this.sensor.getState());
        assertFalse(this.securityAlarm.isOn());
    }

    @Test
    public void shouldTriggerArmedSensor() {
        this.sensor.setState(ArmedSensor.getInstance());

        assertTrue(this.sensor.trigger());
        assertEquals(TriggeredSensor.getInstance(), this.sensor.getState());
        assertTrue(this.securityAlarm.isOn());
    }

    @Test
    public void shouldNotResetArmedSensor() {
        this.sensor.setState(ArmedSensor.getInstance());

        assertFalse(this.sensor.reset());
        assertEquals(ArmedSensor.getInstance(), this.sensor.getState());
        assertFalse(this.securityAlarm.isOn());
    }

    @Test
    public void shouldDisarmArmedSensor() {
        this.sensor.setState(ArmedSensor.getInstance());

        assertTrue(this.sensor.disarm());
        assertEquals(DisarmedSensor.getInstance(), this.sensor.getState());
        assertFalse(this.securityAlarm.isOn());
    }

    /* TriggeredSensor */

    @Test
    public void shouldNotArmTriggeredSensor() {
        this.sensor.setState(TriggeredSensor.getInstance());

        assertFalse(this.sensor.arm());
        assertEquals(TriggeredSensor.getInstance(), this.sensor.getState());
        assertTrue(this.securityAlarm.isOn());
    }

    @Test
    public void shouldNotTriggerTriggeredSensor() {
        this.sensor.setState(TriggeredSensor.getInstance());

        assertFalse(this.sensor.trigger());
        assertEquals(TriggeredSensor.getInstance(), this.sensor.getState());
        assertTrue(this.securityAlarm.isOn());
    }

    @Test
    public void shouldResetTriggeredSensor() {
        this.sensor.setState(TriggeredSensor.getInstance());

        assertTrue(this.sensor.reset());
        assertEquals(ArmedSensor.getInstance(), this.sensor.getState());
        assertFalse(this.securityAlarm.isOn());
    }

    @Test
    public void shouldNotDisarmTriggeredSensor() {
        this.sensor.setState(TriggeredSensor.getInstance());

        assertFalse(this.sensor.disarm());
        assertEquals(TriggeredSensor.getInstance(), this.sensor.getState());
        assertTrue(this.securityAlarm.isOn());
    }

    /* SecurityAlarm */

    @Test
    public void shouldBeInitiallyOff() {
        assertFalse(this.securityAlarm.isOn());
        assertEquals(DisarmedSensor.getInstance(), this.sensor.getState());
    }

    @Test
    public void shouldBeOffWhenArmed() {
        this.sensor.arm();
        assertFalse(this.securityAlarm.isOn());
        assertEquals(ArmedSensor.getInstance(), this.sensor.getState());
    }

    @Test
    public void shouldBeOnWhenArmedAndTriggered() {
        this.sensor.arm();
        this.sensor.trigger();
        assertTrue(this.securityAlarm.isOn());
        assertEquals(TriggeredSensor.getInstance(), this.sensor.getState());
    }

    @Test
    public void shouldBeOffWhenArmedAndTriggeredAndReset() {
        this.sensor.arm();
        this.sensor.trigger();
        this.sensor.reset();
        assertFalse(this.securityAlarm.isOn());
        assertEquals(ArmedSensor.getInstance(), this.sensor.getState());
    }

    @Test
    public void shouldBeOffWhenArmedAndTriggeredAndResetAndDisarmed() {
        this.sensor.arm();
        this.sensor.trigger();
        this.sensor.reset();
        this.sensor.disarm();
        assertFalse(this.securityAlarm.isOn());
        assertEquals(DisarmedSensor.getInstance(), this.sensor.getState());
    }

}
