package project.aigo.myapplication.Object;

import java.util.Vector;

public class Donation {
    public static Vector<Donation> donationList = new Vector<Donation>();

    private String donation_title;
    private int image_id;
    private int video_id;
    private String donation_desc;

    public Donation(String donation_title, int image_id, int video_id, String donation_desc) {
        this.donation_title = donation_title;
        this.image_id = image_id;
        this.video_id = video_id;
        this.donation_desc = donation_desc;
    }

    public static Vector<Donation> getDonationList() {
        return donationList;
    }

    public static void setDonationList(Vector<Donation> donationList) {
        Donation.donationList = donationList;
    }

    public String getDonation_title() {
        return donation_title;
    }

    public void setDonation_title(String donation_title) {
        this.donation_title = donation_title;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getDonation_desc() {
        return donation_desc;
    }

    public void setDonation_desc(String donation_desc) {
        this.donation_desc = donation_desc;
    }
}