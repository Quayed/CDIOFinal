package test.dal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestFormulaCompDAO.class, TestFormulaDAO.class, TestMaterialBatchDAO.class, TestMaterialDAO.class,
		TestOperatorDAO.class, TestProductbatchCompDAO.class, TestProductbatchDAO.class })
public class AllTests {

}
