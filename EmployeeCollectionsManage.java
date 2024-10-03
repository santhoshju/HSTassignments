import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeCollectionsManage {
     // To store collections
    private static Map<String, List<Employee>> collections = new HashMap<>();

    // Employee data
    private static List<Employee> employeeData = new ArrayList<>();

    static {
        employeeData.add(new Employee("E02001", "Alice", "IT", "Female"));
        employeeData.add(new Employee("E02002", "Bob", "HR", "Male"));
        employeeData.add(new Employee("E02003", "Charlie", "IT", "Male"));
        employeeData.add(new Employee("E02004", "David", "Finance", "Male"));
        employeeData.add(new Employee("E02005", "Eve", "HR", "Female"));
    }

    public static void createCollection(String collectionName) {
        collections.put(collectionName, new ArrayList<>());
        System.out.println("Collection " + collectionName + " created.");
    }

    public static void indexData(String collectionName, String excludeColumn) {
        if (!collections.containsKey(collectionName)) {
            System.out.println("Collection " + collectionName + " does not exist.");
            return;
        }

        List<Employee> indexedEmployees = new ArrayList<>();
        for (Employee emp : employeeData) {
            if ("Department".equalsIgnoreCase(excludeColumn)) {
                indexedEmployees.add(new Employee(emp.id, emp.name, null, emp.gender));
            } else if ("Gender".equalsIgnoreCase(excludeColumn)) {
                indexedEmployees.add(new Employee(emp.id, emp.name, emp.department, null));
            } else {
                indexedEmployees.add(emp);
            }
        }
        collections.get(collectionName).addAll(indexedEmployees);
        System.out.println("Data indexed into collection " + collectionName + ", excluding column '" + excludeColumn + "'");
        System.out.println("============================");
    }

    public static void searchByColumn(String collectionName, String columnName, String columnValue) {
        if (!collections.containsKey(collectionName)) {
            System.out.println("Collection " + collectionName + " does not exist.");
            return;
        }

        List<Employee> results = collections.get(collectionName).stream()
                .filter(emp -> {
                    if ("Department".equalsIgnoreCase(columnName)) {
                        return columnValue.equals(emp.department);
                    } else if ("Gender".equalsIgnoreCase(columnName)) {
                        return columnValue.equals(emp.gender);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        System.out.println("Search results in collection " + collectionName + " for " + columnName + " = " + columnValue + ": " + results);
    }

    public static void getEmpCount(String collectionName) {
        if (!collections.containsKey(collectionName)) {
            System.out.println("Collection " + collectionName + " does not exist.");
            return;
        }

        int empCount = collections.get(collectionName).size();
        System.out.println("Employee count in collection " + collectionName + ": " + empCount);
    }

    public static void delEmpById(String collectionName, String employeeId) {
        if (!collections.containsKey(collectionName)) {
            System.out.println("Collection " + collectionName + " does not exist.");
            return;
        }

        collections.get(collectionName).removeIf(emp -> emp.id.equals(employeeId));
        System.out.println("Employee with ID " + employeeId + " deleted from collection " + collectionName);
    }

    public static void getDepFacet(String collectionName) {
        if (!collections.containsKey(collectionName)) {
            System.out.println("Collection " + collectionName + " does not exist.");
            return;
        }

        Map<String, Integer> deptFacet = new HashMap<>();
        for (Employee emp : collections.get(collectionName)) {
            String department = emp.department == null ? "Unknown" : emp.department;
            deptFacet.put(department, deptFacet.getOrDefault(department, 0) + 1);
        }

        System.out.println("Department facets in collection " + collectionName + ": " + deptFacet);
    }

    public static void main(String[] args) {
        String v_nameCollection = "Hash_Santhosh";
        String v_phoneCollection = "Hash_1234";

        createCollection(v_nameCollection);
        createCollection(v_phoneCollection);
        System.out.println("============================");


        getEmpCount(v_nameCollection);

        System.out.println("============================");


        indexData(v_nameCollection, "Department");
        indexData(v_phoneCollection, "Gender");

        System.out.println("============================");


        delEmpById(v_nameCollection, "E02003");

        System.out.println("============================");


        getEmpCount(v_nameCollection);

        System.out.println("============================");


        searchByColumn(v_nameCollection, "Department", "IT");
        searchByColumn(v_nameCollection, "Gender", "Male");
        searchByColumn(v_phoneCollection, "Department", "IT");
        System.out.println("============================");


        getDepFacet(v_nameCollection);
        getDepFacet(v_phoneCollection);
    }
}
