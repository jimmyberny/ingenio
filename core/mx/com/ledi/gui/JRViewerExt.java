package mx.com.ledi.gui;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 * Jun 27, 2013 - 8:51:17 AM
 *
 * @author jimmy
 */
public class JRViewerExt extends JRViewer {

    public JRViewerExt() {
        super(null);
    }

    public void update(JasperPrint jrPrint) {
        viewerContext.clear();
        viewerContext.loadReport(jrPrint);
        viewerContext.refreshPage();
    }
}
