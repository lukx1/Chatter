package net.lukx.jchatter.java.controls;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import net.lukx.jchatter.java.fetching.ContentRepository;
import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RoomPane extends LinedPaneManagerPane<LinedPane> {

    private Repos repos;
    private ContentRepository contentRepository;
    private CurrentValues currentValues;
    private InitArgs args = new ConcreteInitArgs(6,100,60,0);

    private List<InnerRoomClickedHandler> innerRoomClickedHandlers = new ArrayList<>();

    public List<Room> getAllRoomsShown(){
        List<Room> rooms = new ArrayList<>(this.innerElements.size());
        for (LinedPane innerElement : this.innerElements) {
            if(innerElement instanceof InnerRoomPane){
                rooms.add(((InnerRoomPane)innerElement).getRoom());
            }
        }
        return rooms;
    }

    public RoomPane(){
        super();
    }

    public void clearInner(){
        clearInnerElements();
    }

    public void addInnerRoomClickedHandler(InnerRoomClickedHandler h){
        innerRoomClickedHandlers.add(h);
    }

    public void removeInnerRoomClickedHandler(InnerRoomClickedHandler h){
        innerRoomClickedHandlers.remove(h);
    }

    public void init(Repos repos, ContentRepository contentRepository, CurrentValues currentValues){
        this.repos = repos;
        this.contentRepository = contentRepository;
        this.currentValues = currentValues;
    }

    public void showAllRooms() throws IOException, URISyntaxException {
        args = new ConcreteInitArgs(args.getPadding(),this.getWidth(),args.getHeight(),args.getTopMargin());
        for (Room room : repos.getRoomRepo().getRoomsWithUser(currentValues.getCurrentUser().id)) {
            InnerRoomPane irp = new InnerRoomPane(room,args);
            irp.initElements();
            addInnerElement(irp);
        }
    }

    public class InnerRoomClickedEvent{
        private Object source;
        private Room room;

        public InnerRoomClickedEvent(Object source, Room room) {
            this.source = source;
            this.room = room;
        }

        public Room getRoom() {
            return room;
        }

        public Object getSource() {
            return source;
        }
    }

    public interface InnerRoomClickedHandler{
        void innerRoomClicked(InnerRoomClickedEvent e);
    }

    private void fireEvents(InnerRoomClickedEvent e){
        for (InnerRoomClickedHandler innerRoomClickedHandler : innerRoomClickedHandlers) {
            innerRoomClickedHandler.innerRoomClicked(e);
        }
    }

    private void innerRoomClickedHandlerMethod(MouseEvent e){
        Room room = ((InnerRoomPane)e.getSource()).getRoom();
        fireEvents( new InnerRoomClickedEvent(
            e.getSource(),room
        ));
    }

    public class InnerRoomPane extends LinedPane {

        private Circle pictureCircle;
        private Label headerLabel;
        private Room room;

        public Room getRoom(){return room;}

        public InnerRoomPane(Room room,InitArgs initArgs){
            super(initArgs);
            this.room = room;
        }


        private void setInner() throws IOException, URISyntaxException {
            if(room.oneOnOne){
                User otherUser = RoomUtils.getOtherUserInRoom(repos,room,currentValues.getCurrentUser());
                pictureCircle.setFill(new ImagePattern(contentRepository.fetchImageWithFallback(otherUser.picture)));
                headerLabel.setText(otherUser.login+" - "+ otherUser.firstName);
            }
            else {
                pictureCircle.setFill(new ImagePattern(contentRepository.fetchImageWithFallback(room.picture)));
                headerLabel.setText(room.name);
            }
        }

        @Override
        protected void initElements() throws IOException, URISyntaxException {
            pictureCircle = new Circle();
            headerLabel = new Label();

            this.createCenterLeftCircle(pictureCircle);
            this.createHeaderLabelNextToPicture(headerLabel);

            setInner();

            this.getStyleClass().add("CursorHand");

            this.addEventHandler(MouseEvent.MOUSE_CLICKED,RoomPane.this::innerRoomClickedHandlerMethod);
        }
    }

}
