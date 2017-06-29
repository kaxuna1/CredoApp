package credo.ge.credoapp.views;


import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.Collections;
import java.util.List;

import credo.ge.credoapp.R;
import io.mattcarroll.hover.Content;
import io.mattcarroll.hover.HoverMenu;
import io.mattcarroll.hover.HoverView;
import io.mattcarroll.hover.window.HoverMenuService;

/**
 * Created by kaxge on 6/29/2017.
 */

public class SingleSectionHoverMenuService extends HoverMenuService {

    private static final String TAG = "SingleSectionNotificationHoverMenuService";

    @Override
    protected int getForegroundNotificationId() {
        return 1000;
    }

    @Nullable
    @Override
    protected Notification getForegroundNotification() {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.tab_background)
                .setContentTitle("Credo")
                .setContentText("PDF გახსნილია")
                .build();
    }

    @Override
    protected void onHoverMenuLaunched(@NonNull Intent intent, @NonNull final HoverView hoverView) {
        hoverView.setMenu(createHoverMenu());
        hoverView.collapse();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        hoverView.expand();
                    }
                },
                1000);
    }

    @NonNull
    private HoverMenu createHoverMenu() {
        return new SingleSectionHoverMenu(getApplicationContext());
    }

    private static class SingleSectionHoverMenu extends HoverMenu {

        private Context mContext;
        private Section mSection;

        private SingleSectionHoverMenu(@NonNull Context context) {
            mContext = context;

            mSection = new Section(
                    new SectionId("1"),
                    createTabView(),
                    createScreen()
            );
        }

        private View createTabView() {
            ImageView imageView = new ImageView(mContext);


            Drawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.WHITE)
                    .bold()
                    .toUpperCase()
                    .fontSize(50)
                    .withBorder(12) /* thickness in px */
                    .endConfig()
                    .buildRound("PDF", ColorGenerator.MATERIAL.getRandomColor() );

            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return imageView;
        }

        private Content createScreen() {
            return new HoverMenuScreen(mContext, "Credo Pdf open");
        }

        @Override
        public String getId() {
            return "singlesectionmenu_foreground";
        }

        @Override
        public int getSectionCount() {
            return 1;
        }

        @Nullable
        @Override
        public Section getSection(int index) {
            if (0 == index) {
                return mSection;
            } else {
                return null;
            }
        }

        @Nullable
        @Override
        public Section getSection(@NonNull SectionId sectionId) {
            if (sectionId.equals(mSection.getId())) {
                return mSection;
            } else {
                return null;
            }
        }

        @NonNull
        @Override
        public List<Section> getSections() {
            return Collections.singletonList(mSection);
        }
    }
}
