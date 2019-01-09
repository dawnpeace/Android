package com.example.dawnpeace.spota_android.Classes;

import java.util.List;

public class AnnouncementList {
    private List<Announcement> unread_announcements;
    private List<Announcement> read_announcements;

    AnnouncementList(List<Announcement> unread_announcements, List<Announcement> read_announcements){
        this.read_announcements = read_announcements;
        this.unread_announcements = unread_announcements;
    }

    public List<Announcement> getRead_announcements() {
        return read_announcements;
    }

    public List<Announcement> getUnread_announcements() {
        return unread_announcements;
    }

    public List<Announcement> getConcatList(){
        List<Announcement> announcements = unread_announcements;
        announcements.addAll(read_announcements);
        return announcements;
    }
}
