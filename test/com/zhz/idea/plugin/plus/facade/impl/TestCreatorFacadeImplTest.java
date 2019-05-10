package  com.zhz.idea.plugin.plus.facade.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.java.stubs.PsiMethodStub;
import com.intellij.psi.impl.java.stubs.impl.PsiMethodStubImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.PsiMethodImpl;
import com.zhz.idea.plugin.plus.domain.aggregate.ClassAgg;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestCreatorFacadeImplTest  { 

    private TestCreatorFacadeImpl service = new TestCreatorFacadeImpl();

    @Test
    public void analysisClassInfo(){ 
        AnActionEvent e = null ;
        ClassAgg classAgg = service.analysisClassInfo(e);

    }

    @Test
    public void isMethodVisible(){ 
        PsiMethod method = null ;
        boolean bool = service.isMethodVisible(method);

    }

    @Test
    public void filterExistsTestMethod(){ 
        List<Meth> target = new ArrayList<>();
        target.add(new Meth(1,true));
        target.add(new Meth(2,true));
        target.add(new Meth(3,false));
        target.add(new Meth(4,true));
        List<Meth> existsList = new ArrayList<>();
        existsList.add(new Meth(1,true));
        existsList.add(new Meth(2,true));
        List<Meth> listPsiMethod = target.stream().filter(method -> (method.visible && !isMethIn(method, existsList))).collect(Collectors.toList());
        List<Meth> listVisible = target.stream().filter(method -> (method.visible)).collect(Collectors.toList());

        System.out.println(1);
    }

    private class Meth {
        private boolean visible;
        private int id;
        public Meth(int id,boolean visible){this.id = id;this.visible=visible;}

        @Override
        public boolean equals(Object obj) {
            if (obj!=null && obj instanceof Meth){
                return this.id == ((Meth) obj).id && this.visible == ((Meth) obj).visible;
            }
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    private boolean isMethIn(Meth m,List<Meth> existList){
        for (Meth meth : existList) {
            if (m.id == meth.id){
                return true;
            }
        }
        return false;
    }





    @Test
    public void appendTestMethodText() throws IOException {
        PsiClass psiTestClass = null ;
        ClassAgg classAgg = null ;
        service.appendTestMethodText(psiTestClass,classAgg);

    }

    @Test
    public void classInfoFromPsiFile(){ 
        PsiJavaFileImpl javaFile = null ;
        ClassAgg classAgg = service.classInfoFromPsiFile(javaFile);

    }

}