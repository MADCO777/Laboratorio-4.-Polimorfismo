package BMW;

// Clase principal para demostración
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ClimateControl climateControl;

    public static void main(String[] args) {
        try {
            selectVehicleClass();
            while (true) {
                showMainMenu();
                int option = getIntInput("Seleccione una opción: ");
                processMainMenuOption(option);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void selectVehicleClass() {
        System.out.println("=== BMW Sistema de Climatización ===");
        System.out.println("1. Clase A (Gama Alta)");
        System.out.println("2. Clase B (Gama Media)");
        System.out.println("3. Clase C (Gama Básica)");

        int classOption = getIntInput("Seleccione la clase de vehículo: ");

        switch (classOption) {
            case 1:
                climateControl = new ClassAClimateControlImpl();
                break;
            case 2:
                climateControl = new ClassBClimateControlImpl();
                break;
            case 3:
                climateControl = new ClassCClimateControlImpl();
                break;
            default:
                System.out.println("Opción inválida. Seleccionando Clase C por defecto.");
                climateControl = new ClassCClimateControlImpl();
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Menú Principal ===");
        System.out.println("1. Encender/Apagar sistema");
        System.out.println("2. Ajustar temperatura");
        System.out.println("3. Modo Automático");
        System.out.println("4. Control de ventilación");
        System.out.println("5. Modo calefacción");
        System.out.println("6. Distribución de aire");
        System.out.println("7. Desempañador");
        System.out.println("8. Funciones especiales");
        System.out.println("9. Mantenimiento");
        System.out.println("0. Salir");
    }

    private static void processMainMenuOption(int option) throws SystemOffException {
        switch (option) {
            case 0:
                System.out.println("Gracias por usar el sistema de climatización BMW");
                System.exit(0);
                break;
            case 1:
                toggleSystem();
                break;
            case 2:
                adjustTemperature();
                break;
            case 3:
                setAutoMode();
                break;
            case 4:
                showVentilationMenu();
                break;
            case 5:
                showHeatingMenu();
                break;
            case 6:
                climateControl.setAirDistribution();
                break;
            case 7:
                climateControl.toggleDefrost();
                break;
            case 8:
                showSpecialFunctions();
                break;
            case 9:
                showMaintenanceMenu();
                break;
            default:
                System.out.println("Opción inválida");
        }
    }

    private static void showMaintenanceMenu() {
    }

    private static void toggleSystem() throws SystemOffException {
        if (climateControl.isOn()) {
            climateControl.turnOff();
            System.out.println("Sistema apagado");
        } else {
            climateControl.turnOn();
            System.out.println("Sistema encendido");
        }
    }

    private static void adjustTemperature() throws SystemOffException {
        int temp = getIntInput("Ingrese la temperatura deseada (16-30°C): ");
        try {
            climateControl.adjustTemperature(temp);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void setAutoMode() throws SystemOffException {
        double extTemp = getDoubleInput("Ingrese la temperatura exterior actual: ");
        climateControl.setAutoMode(extTemp);
    }

    private static void showVentilationMenu() throws SystemOffException {
        System.out.println("\n=== Control de Ventilación ===");
        if (climateControl instanceof ClassAClimateControl) {
            System.out.println("1. Ajustar nivel (1-5)");
            System.out.println("2. Ventilación direccional");
            System.out.println("3. Ventilación automática");
            System.out.println("4. Modo circulación interna");
        } else if (climateControl instanceof ClassBClimateControl) {
            System.out.println("1. Ajustar nivel (1-3)");
            System.out.println("2. Ventilación direccional");
            System.out.println("3. Modo ECO");
        } else {
            System.out.println("1. Ajustar nivel (Bajo/Medio/Alto)");
            System.out.println("2. Ventilación a zona específica");
            System.out.println("3. Modo silencioso");
        }

        int option = getIntInput("Seleccione una opción: ");
        processVentilationOption(option);
    }

    private static void processVentilationOption(int option) throws SystemOffException {
        if (climateControl instanceof ClassAClimateControl) {
            ClassAClimateControl classA = (ClassAClimateControl) climateControl;
            switch (option) {
                case 1:
                    int level = getIntInput("Ingrese nivel de ventilación (1-5): ");
                    classA.setVentilationLevel(VentilationLevel.values()[level-1]);
                    break;
                case 2:
                    Set<AirDirection> directions = selectAirDirections();
                    classA.setDirectionalVentilation(directions);
                    break;
                case 3:
                    double internalTemp = getDoubleInput("Temperatura interior actual: ");
                    classA.setAutoVentilation(internalTemp, AirQuality.GOOD);
                    break;
                case 4:
                    classA.toggleInternalCirculation();
                    break;
            }
        }
        // Similar para ClassB y ClassC...
    }

    private static Set<AirDirection> selectAirDirections() {
        Set<AirDirection> directions = new HashSet<>();
        System.out.println("Seleccione direcciones (separadas por coma):");
        System.out.println("1. Parabrisas");
        System.out.println("2. Frontal");
        System.out.println("3. Pies");

        String[] selections = scanner.nextLine().split(",");
        for (String sel : selections) {
            int dir = Integer.parseInt(sel.trim());
            directions.add(AirDirection.values()[dir-1]);
        }
        return directions;
    }

    private static void showHeatingMenu() throws SystemOffException {
        System.out.println("\n=== Control de Calefacción ===");
        System.out.println("1. Calefacción rápida");

        if (climateControl instanceof ClassAClimateControl ||
                climateControl instanceof ClassBClimateControl) {
            System.out.println("2. Calefacción de asientos");
        }

        if (climateControl instanceof ClassAClimateControl) {
            System.out.println("3. Calefacción de volante");
            System.out.println("4. Control de zona dual");
        }

        int option = getIntInput("Seleccione una opción: ");
        processHeatingOption(option);
    }

    private static void processHeatingOption(int option) {
    }

    private static void showSpecialFunctions() throws SystemOffException {
        if (climateControl instanceof ClassAClimateControl) {
            showClassASpecialFunctions();
        } else if (climateControl instanceof ClassCClimateControl) {
            showClassCSpecialFunctions();
        } else {
            System.out.println("No hay funciones especiales disponibles para este modelo.");
        }
    }

    private static void showClassASpecialFunctions() throws SystemOffException {
        ClassAClimateControl classA = (ClassAClimateControl) climateControl;
        System.out.println("\n=== Funciones Especiales Clase A ===");
        System.out.println("1. Configurar ionizador");
        System.out.println("2. Gestionar perfiles de usuario");

        int option = getIntInput("Seleccione una opción: ");
        switch (option) {
            case 1:
                showIonizerMenu(classA);
                break;
            case 2:
                showProfileMenu(classA);
                break;
        }
    }

    private static void showProfileMenu(ClassAClimateControl classA) {
    }

    private static void showIonizerMenu(ClassAClimateControl classA) {
    }

    private static void showClassCSpecialFunctions() throws SystemOffException {
        ClassCClimateControl classC = (ClassCClimateControl) climateControl;
        System.out.println("\n=== Control de Humedad ===");
        System.out.println("1. Ajustar nivel de humedad");
        System.out.println("2. Ver estado actual");

        int option = getIntInput("Seleccione una opción: ");
        switch (option) {
            case 1:
                System.out.println("Seleccione nivel: 1-Bajo, 2-Medio, 3-Alto");
                int level = getIntInput("Nivel: ");
                classC.setHumidityLevel(HumidityLevel.values()[level-1]);
                break;
            case 2:
                classC.showHumidityStatus();
                break;
        }
    }

    private static int getIntInput(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double getDoubleInput(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    // Otros métodos auxiliares según sea necesario...
}