package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
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
		
		//Tranzíciók listázása 2.3
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
		}
		

		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
