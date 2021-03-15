package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.InterfaceScope;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static Statechart readModel(String src) {
		Resource resource = (new ResourceSetImpl()).getResource(URI.createURI(src), true);
		EObject content = resource.getContents().get(0);
		Statechart statechart = (Statechart) content;
		return statechart;
	}
	
	public static void print_4_4(Statechart s) {
		TreeIterator<EObject> iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof InterfaceScope) {
				InterfaceScope is = (InterfaceScope) content;
				System.out.println("public static void print(IExampleStatemachine s) {");
				for(Property p : is.getVariables()) {
					System.out.println("System.out.println(\""+ String.valueOf(p.getName().charAt(0)).toUpperCase() + " = \" + s.getSCInterface().get" + p.getName().substring(0, 1).toUpperCase() + p.getName().substring(1) + "());");
				}
				System.out.println("}");
			}
		}
	}
	
	public static void print_4_5() {
		Statechart statechart = readModel("./model_input/example.sct");
		TreeIterator<EObject> iterator = statechart.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof InterfaceScope) {
				InterfaceScope is = (InterfaceScope) content;
				System.out.println("public static void main(String[] args) throws IOException {\n");
				System.out.println("\tExampleStatemachine s = new ExampleStatemachine();\r\n" + 
						"\ts.setTimer(new TimerService());\r\n" + 
						"\tRuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
						"\ts.init();\r\n" + 
						"\ts.enter();");
				System.out.println("\tScanner scan = new Scanner(System.in);");
				System.out.println("\tString input = \"\";");
				System.out.println("\twhile(!input.equals(\"exit\")) {");
				System.out.println("\t\tinput = scan.nextLine();");
				System.out.println("\t\tswitch(input) {");
				for(Event e : is.getEvents()) {
					System.out.print("\t\t\tcase \"" + e.getName() + "\":\n\t\t\t\ts.raise"+ e.getName().substring(0, 1).toUpperCase() + e.getName().substring(1) +"();");
					System.out.println("\n\t\t\t\ts.runCycle();\n\t\t\t\tprint(s);\n\t\t\t\tbreak;");
				}
				System.out.println("\t\t\tdefault:");
				System.out.println("\t\t\t\tprint(s);");
				System.out.println("\t\t}");
				System.out.println("\t}");
				System.out.println("\tSystem.exit(0);");
				System.out.println("}");
			}
		}
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				System.out.println(state.getName());
			}
		}
		
		/*//Tranzíciók listázása 2.3
		System.out.println("-----Tranzíciók-----");
		iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				EList<Transition> outgoing = state.getOutgoingTransitions();
				for(Transition t : outgoing) {
					System.out.println(t.getSource().getName() + " -> " + t.getTarget().getName());
				}
			}
		}
		
		// 2.4
		System.out.println("-----Csapda állapotok-----");
		iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				EList<Transition> outgoing = state.getOutgoingTransitions();
				if(outgoing.isEmpty()) {
					System.out.println(state.getName());
				}
			}
		}
		
		//2.5
		System.out.println("-----Javasolt nevek-----");
		iterator = s.eAllContents();
		ArrayList<String> names = new ArrayList<String>();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				names.add(state.getName());
			}
		}
		iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				if (state.getName() == "") {
					int number = 0;
					while(names.contains("State" + number)) {
						number++;
					}
					names.add("State" + number);
					System.out.println("State" + number);
				}
			}
		}*/
		
		/*iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof InterfaceScope) {
				InterfaceScope is = (InterfaceScope) content;
				for(Property p : is.getVariables()) {
					System.out.println(p.getName());
				}
				for(Event e : is.getEvents()) {
					System.out.println(e.getName());
				}
			}
		}*/
		
		print_4_5();
		print_4_4(s);

		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
