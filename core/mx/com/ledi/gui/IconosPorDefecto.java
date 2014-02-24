package mx.com.ledi.gui;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author Tony Stark -- Ironman --
 */
public class IconosPorDefecto implements IconSet {

    private Map<String, ImageIcon> cache;

    public IconosPorDefecto() {
        cache = new HashMap<String, ImageIcon>(40);
    }

    /**
     *
     * @param fileName
     * @return
     */
    public ImageIcon getIcon(String fileName) {
        ImageIcon ii = cache.get(fileName);
        if (ii == null) {
            ii = new ImageIcon(getClass().getResource("/com/ingenio/imagenes/" + fileName));
            cache.put(fileName, ii);
        }
        return ii;
    }

    @Override
    public ImageIcon getNoIcon() {
        return getIcon("database_add.png");
    }

    @Override
    public ImageIcon getAppIcon() {
        return getIcon("application.png");
    }

    @Override
    public ImageIcon getSaveIcon() {
        return getIcon("database_save.png");
    }

    @Override
    public ImageIcon getBackIcon() {
        return getIcon("arrow_left.png");
    }

    @Override
    public ImageIcon getAddIcon() {
        return getIcon("database_add.png");
    }

    @Override
    public ImageIcon getDeleteIcon() {
        return getIcon("database_delete.png");
    }

    @Override
    public ImageIcon getDownloadIcon() {
        return getIcon("download.png");
    }

    @Override
    public ImageIcon getResetIcon() {
        return getIcon("database_refresh.png");
    }

    @Override
    public ImageIcon getFindIcon() {
        return getIcon("google_custom_search.png");
    }

    @Override
    public ImageIcon getCancelIcon() {
        return getIcon("cancel.png");
    }

    @Override
    public ImageIcon getExecuteIcon() {
        return getIcon("lightning.png");
    }

    @Override
    public ImageIcon getOkIcon() {
        return getIcon("tick_octagon.png");
    }

}
