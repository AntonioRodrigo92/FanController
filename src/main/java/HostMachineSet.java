import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostMachineSet {
    private Map<String, HostMachine> machines;

    public HostMachineSet(List<HostMachine> machineList) {
        machines = new HashMap<>(machineList.size());
        for (HostMachine machine : machineList) {
            String hostname = machine.getHostname();
            machines.put(hostname, machine);
        }
    }

    public void updateReadingMap(JsonObject jsonObject) {
        String hostname = jsonObject.get("hostname").getAsString();
        HostMachine machine = machines.get(hostname);
        machine.updateReading(jsonObject);
    }

    public boolean triggerFanOn() {
        for (HostMachine machine : machines.values()) {
            if(machine.isEnabled() &&
//                machine.getReading() != null &&
                machine.getReading().getTemperature() >= machine.getTriggerOnTemp() &&
                ! machine.getReading().isReadingObsolete()) {
                return true;
            }
        }
        return false;
    }

    public boolean triggerFanOff() {
        for (HostMachine machine : machines.values()) {
            if (machine.isEnabled() &&
//                machine.getReading() != null &&
                machine.getReading().getTemperature() > machine.getTriggerOffTemp() &&
                ! machine.getReading().isReadingObsolete()) {
                return false;
            }
        }
        return true;
    }

    public Map<String, HostMachine> getMachineSet() {
        return machines;
    }

}
