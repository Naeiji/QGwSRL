import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class RuleBasedGenerator {
    //Defining the semantic role labels
    public static boolean A1;
    public static boolean A2;

    public static boolean A1PPT;
    public static boolean A2PPT;

    public static boolean A1PRD;
    public static boolean A2PRD;
    public static boolean A1DIR;
    public static boolean A2DIR;
    public static boolean A3DIR;
    public static boolean AMDIR;

    public static boolean A0PAG;
    public static boolean A1PAG;

    public static boolean A0CAU;

    public static boolean A1GOL;
    public static boolean A2GOL;
    public static boolean A4GOL;
    public static boolean AMGOL;
    public static boolean AMMOD;
    public static boolean AMNEG;
    public static boolean AMTMP;
    public static boolean AMLOC;
    public static boolean AMADV;
    public static boolean AMMNR;
    public static boolean A2PRP;
    public static String A2PRP_phrase;
    public static boolean AMPRP;
    public static boolean CV;
    public static String CV_phrase;
    public static boolean AMPNC;
    public static boolean A1LOC;
    public static String A1LOC_phrase;
    public static boolean RAMTMP;
    public static String RAMTMP_phrase;
    public static boolean RA2PRD;
    public static String RA2PRD_phrase;
    public static boolean A2EXT;
    public static String A2EXT_phrase;
    public static String AMPNC_phrase;
    public static String A1_phrase;
    public static String A2_phrase;
    public static String A2LOC_phrase;
    public static boolean A2LOC;
    public static String AMPRD_phrase;
    public static boolean AMPRD;
    public static String A1PPT_phrase;
    public static String A2PPT_phrase;
    public static String A1PRD_phrase;
    public static String A2PRD_phrase;
    public static String A1DIR_phrase;
    public static String A2DIR_phrase;
    public static String A3DIR_phrase;
    public static String AMDIR_phrase;

    public static String A0PAG_phrase;
    public static String A1PAG_phrase;

    public static String A0CAU_phrase;

    public static String A1GOL_phrase;
    public static String A2GOL_phrase;
    public static String A4GOL_phrase;
    public static String AMGOL_phrase;

    public static String AMMOD_phrase;
    public static String AMNEG_phrase;
    public static String AMTMP_phrase;
    public static String AMLOC_phrase;
    public static String AMADV_phrase;
    public static String AMMNR_phrase;

    public static String AMPRP_phrase;

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    static void reset() {
        A1 = false;
        A2 = false;

        A1PPT = false;
        A2PPT = false;
        A1PRD = false;
        A2PRD = false;
        A1DIR = false;
        A2DIR = false;
        A3DIR = false;
        AMDIR = false;

        A0PAG = false;
        A1PAG = false;

        A0CAU = false;

        A1GOL = false;
        A2GOL = false;
        A4GOL = false;
        AMGOL = false;
        AMMOD = false;
        AMNEG = false;
        AMTMP = false;
        AMLOC = false;
        AMADV = false;
        AMMNR = false;
        AMPRP = false;
        AMPRD = false;
        AMPRD_phrase = null;
        CV = false;
        CV_phrase = null;
        A1LOC = false;
        A1LOC_phrase = null;
        A2LOC = false;
        A2LOC_phrase = null;
        A2PRP = false;
        A2PRP_phrase = null;
        AMPNC = false;
        AMPNC_phrase = null;
        A2EXT = false;
        A2EXT_phrase = null;
        RAMTMP = false;
        RAMTMP_phrase = null;
        RA2PRD = false;
        RA2PRD_phrase = null;
        AMPRP_phrase = null;
        A1_phrase = null;
        A2_phrase = null;

        A1PPT_phrase = null;
        A2PPT_phrase = null;
        A1PRD_phrase = null;
        A2PRD_phrase = null;
        A1DIR_phrase = null;
        A2DIR_phrase = null;
        A3DIR_phrase = null;
        AMDIR_phrase = null;

        A0PAG_phrase = null;
        A1PAG_phrase = null;

        A0CAU_phrase = null;

        A1GOL_phrase = null;
        A2GOL_phrase = null;
        A4GOL_phrase = null;
        AMGOL_phrase = null;
        AMMOD_phrase = null;
        AMNEG_phrase = null;
        AMTMP_phrase = null;
        AMLOC_phrase = null;
        AMADV_phrase = null;
        AMMNR_phrase = null;
    }

    public static void main(String[] args) {
        try {
            // Reading from this file:
            BufferedReader inputFile = new BufferedReader(new FileReader("input.txt"));

            // Writing to this file:
            BufferedWriter Questions = new BufferedWriter(new FileWriter("output.txt"));

            String inputLine = inputFile.readLine();
            String content = null, target = null, terget_v1 = null;
            int counter;
            char flag = '\0';
            int no = 0;//number of QA
            int track = 0;
            //int notCovered=0;
            while (inputLine != null) {

                String[] temp = inputLine.split("\t+");


                counter = 1;//That's where the target starts //****** it was  2, changed by muath to 1
                try {
                    //System.out.println(temp.length);

                    if (inputLine.matches("^\\d+.*")) {
//                    	System.out.println("inputLine: " + inputLine);
                        track = track + 1;
                        Questions.write("------------------------\n");

//                    	System.exit(0);

                        //if (temp.length == 2 ) {
                        content = temp[1];
                        //reset();//move by muath
                    } else if (temp.length > 2) {
                        reset();
                        flag = '\0';
                        String temp_target = temp[counter];
                        String[] temtar = temp_target.split("\\.");
                        terget_v1 = temtar[0];
                        if (terget_v1.startsWith("*")) {
                            flag = '*';
                            String[] terget_v2 = terget_v1.split(" ");
                            target = terget_v2[1];

                        } else {
                            target = terget_v1;
                        }
                        //System.out.println("target:" +target);

                        while (counter < temp.length - 1) {

                            counter += 1;//skip the space after the phrase
                            String tag = null, word = null;
                            String[] rel = temp[counter].split("\\(");
                            String[] srel = rel[0].split(" ");
                            //System.out.println("rel="+rel.length);
                            String[] phrase = rel[1].split("\\)");

                            tag = srel[0];
                            word = phrase[0];
                            System.out.println("***" + tag + " " + word + " " + inputLine + "\n\n\n");

                            if (target.matches("show"
                                    + "")) {
                                System.out.println("YES");
                            }

                            //temps.phrases=phrase[0];
                            ////temps.tags=srel[0];
                            //setting the right flags
                            //These flags represent the semantic role labels
                            System.out.println("tag=" + tag);
                            if (tag.matches("A2")) {
                                A2 = true;
                                A2_phrase = word;
                            } else if (tag.matches("A0=CAU")) {

                                A0CAU = true;
                                A0CAU_phrase = word;
                            } else if (tag.matches("A1=PPT")) {
                                A1PPT = true;
                                A1PPT_phrase = word;
                                if (A1PPT_phrase.startsWith("your")) {
                                    A1PPT_phrase = A1PPT_phrase.replaceFirst("your", "my");

                                }
                            } else if (tag.matches("A2=PPT")) {
                                A2PPT = true;
                                A2PPT_phrase = word;
                                if (A2PPT_phrase.startsWith("your")) {
                                    A2PPT_phrase = A2PPT_phrase.replaceFirst("your", "my");

                                }
                            } else if (tag.matches("AM-TMP")) {
                                if (AMTMP != true) {
                                    AMTMP = true;
                                    AMTMP_phrase = word;
                                }
                                //System.out.println( " AMTMP=" + AMTMP);///////////////////////////////////////
                            } else if (tag.matches("A1=PRD")) {
                                A1PRD = true;
                                A1PRD_phrase = word;
                            } else if (tag.matches("A2=PRD")) {
                                A2PRD = true;
                                A2PRD_phrase = word;
                            } else if (tag.matches("A1=DIR")) {
                                A1DIR = true;
                                A1DIR_phrase = word;
                            } else if (tag.matches("AM-MOD")) {
                                AMMOD = true;
                                AMMOD_phrase = word;
                            } else if (tag.matches("A1=GOL")) {
                                A1GOL = true;
                                A1GOL_phrase = word;
                            } else if (tag.matches("A2=GOL")) {
                                A2GOL = true;
                                A2GOL_phrase = word;
                            } else if (tag.matches("A4=GOL")) {
                                A4GOL = true;
                                A4GOL_phrase = word;
                            } else if (tag.matches("AM-GOL")) {
                                AMGOL = true;
                                AMGOL_phrase = word;
                            } else if (tag.matches("A2=PRD")) {
                                A2PRD = true;
                                A2PRD_phrase = word;
                            } else if (tag.matches("A0=PAG")) {
                                System.out.println("point3");
                                A0PAG = true;
                                A0PAG_phrase = word;
                                //System.out.println("A0PAG="+A0PAG );
                            } else if (tag.matches("A1=PAG")) {
                                A1PAG = true;
                                A1PAG_phrase = word;
                            } else if (tag.matches("AM-NEG")) {
                                AMNEG = true;
                                AMNEG_phrase = word;
                            } else if (tag.matches("AM-LOC")) {
                                AMLOC = true;
                                AMLOC_phrase = word;
                            } else if (tag.matches("A3=DIR")) {
                                A3DIR = true;
                                A3DIR_phrase = word;
                            } else if (tag.matches("AM-ADV")) {
                                AMADV = true;
                                AMADV_phrase = word;
                            } else if (tag.matches("AM-MNR")) {
                                AMMNR = true;
                                AMMNR_phrase = word;
                            } else if (tag.matches("A2=DIR")) {
                                A2DIR = true;
                                A2DIR_phrase = word;
                            } else if (tag.matches("AM-PRP")) {
                                AMPRP = true;
                                AMPRP_phrase = word;
                            } else if (tag.matches("A1")) {
                                A1 = true;
                                A1_phrase = word;
                            } else if (tag.matches("AM-DIR")) {
                                AMDIR = true;
                                AMDIR_phrase = word;
                            } else if (tag.matches("C-V")) {
                                CV = true;//Muath
                                CV_phrase = word;//Muath
                            } else if (tag.matches("A2=PRP")) {
                                A2PRP = true;//Muath
                                A2PRP_phrase = word;//Muath
                            } else if (tag.matches("AM-PNC")) {
                                AMPNC = true;


                                AMPNC_phrase = word;

                            } else if (tag.matches("A2=EXT")) {
                                A2EXT = true;//muath
                                A2EXT_phrase = word;//muath
                            } else if (tag.matches("A2=LOC")) {
                                A2LOC = true;//muath
                                A2LOC_phrase = word;//muath
                            } else if (tag.matches("A1=LOC")) {
                                A1LOC = true;
                                A1LOC_phrase = word;
                            } else if (tag.matches("R-AM-TMP")) {
                                RAMTMP = true;
                                RAMTMP_phrase = word;
                            } else if (tag.matches("R-A2=PRD")) {
                                RA2PRD = true;
                                RA2PRD_phrase = word;
                            } else if (tag.matches("AM-PRD")) {
                                AMPRD = true;//Muath
                                AMPRD_phrase = word;//Muath
                            }
                            //System.out.println("point2");
                            //System.out.println("A0PAG="+A0PAG + " AMTMP=" + AMTMP);
                        }

                        /**
                         * ***************************************************************************************
                         */
                        /**
                         * ************************ Rules for Generating Questions********************************
                         */
                        /**
                         * ***************************************************************************************
                         */
                        //reading the sentence is done.
                        //we need to create questions
                        //rules.txt
                        /*Q1: [TARGET][A1=PPT][AM-TMP]
								What do you ....[TARGET]...[AM-TMP]? 
								A1=PPT
								Q:What do you adjust Before starting your vehicle?
								A:your seat.
								*
								*
                         */
                        if (flag == '*') {
                            Questions.write(flag);
                        }
                        //what can happen if i crank the engine for a long period of time ?
                        //what can happen if i AM-PRD?
                        //AM-PRD
                        //AM-MNR
                        //AM-ADV
                        if (AMPRD && AMMNR && AMADV) {
                            Questions.write("\t" + "rule 54d" + "\t" + "What can happen if i " + AMPRD_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        ////////////////////////////////////////////////
                        if (A1PPT && A2LOC && AMMOD) {
                            Questions.write("\t" + "rule 54c" + "\t" + "What can happen if i " + A1PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        ///what can happen if i continue to drive with worn out brake pads  
                        //what can happen if i A1=PPT?
                        //A1=PPT
                        //A2=LOC
                        //AM-MOD
                        /////////////////////////////////////////////////////
                        if (A0PAG && A1PPT && AMMOD) {
                            Questions.write("\t" + "rule 54b" + "\t" + "What can happen if a " + A0PAG_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //what can happen if a seat belt is twisted ?
                        //what can happen if A0=PAG?
                        //A0=PAG
                        //A1=PPT
                        //AM-MOD
                        /////////////////////////////////////////////////////////////
                        if (A0CAU && AMTMP && AMADV && A1PPT) {
                            Questions.write("\t" + "rule 54a" + "\t" + "What can happen if a " + A0CAU_phrase + " is used " + AMTMP_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //what can happen if a heavy brake is used when going down a hill ?
                        //What can happen if a A0=CAU is used AM-TMP
                        /////////////////////////////////////////
                        //System.out.println("Helllllllllooooooo\n");
                        //System.out.println(A0PAG + " " + AMTMP);
                        if (A2PRD && A1PPT && AMTMP && target.equals("be")) {

                            Questions.write("\t" + "rule 53" + "\t" + "Is " + A1PPT_phrase + " " + A2PRD_phrase + " " + AMTMP_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //is the all-wheel drive active all the time ?
                        //is A1=PPT A2=PRD AM-TMP?
                        if (A2PRD && A1PPT && target.equals("be")) {

                            Questions.write("\t" + "rule 52" + "\t" + "Is it " + A2PRD_phrase + " for " + A1PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        ///################should be changed or cancelled
                        //is it normal for the brakes to make a screeching sound ?
                        //is it A2=PRD for A1=PPT?

                        if (A1PPT && RA2PRD) {

                            Questions.write("\t" + "rule 51a" + "\t" + "How will i know that " + A1PPT_phrase + " is " + RA2PRD_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                            Questions.write("\t" + "rule 51b" + "\t" + "How do I know if " + A1PPT_phrase + " is " + RA2PRD_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how will i know that the traction control system is on ?
                        //how will i know that A1=PPT is R-A2=PRD? 

                        if (A1PPT && CV && RAMTMP) {

                            Questions.write("\t" + "rule 50" + "\t" + "How do I know if " + A1PPT_phrase + " is " + CV_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //How do I know if the airbag is off?
                        //How do I know if A1=PPT is C-V? A1=PPT C-V R-AM-TMP

                        if (A1PPT && AMTMP && AMMNR) {

                            Questions.write("\t" + "rule 49" + "\t" + "how should i " + target + "  " + A1PPT_phrase + " " + AMTMP_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how should i apply the accelerator on ice ?
                        //how should i target A1=PPT AM-TMP? A1=PPT AM-TMP AM-MNR 
                        if (A1 && AMGOL && AMTMP) {

                            Questions.write("\t" + "rule 48" + "\t" + "how often should i " + target + "  " + A1_phrase + " " + AMGOL_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how often should i check the tires for wear ? 
                        //how often should i target A1 AM-GOL? A1 AM-GOL AM-TMP

                        if (A1LOC && AMMNR) {

                            Questions.write("\t" + "rule 47" + "\t" + "how often should i " + target + "  " + A1LOC_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how often should i check the engine oil level ?
                        //how often should i target A1=LOC? A1=LOC  AM-MNR

                        if (A1PPT && A0PAG && AMADV) {
                            String[] arr = AMADV_phrase.split(" ", 2);
                            if (arr[0].equals("unlike"))
                                AMADV_phrase = arr[1];
                            Questions.write("\t" + "rule 46" + "\t" + "how is " + A0PAG_phrase + " different from " + AMADV_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how is trailering different from driving the vehicle by itself ? 
                        //how is A0=PAG different from AM-ADV ? A0=PAG A1=PPT AM-ADV
                        if (A1PPT && A2GOL) {
                            Questions.write("\t" + "rule 45" + "\t" + "how does " + A2GOL_phrase + " affect " + A1PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how does weather affect cruise control ?
                        //how does A2=GOL affect A1=PPT?
                        if (A0PAG && A1PPT && A1PPT && AMMNR && AMMOD) {
                            Questions.write("\t" + "rule 44" + "\t" + "how does " + A0PAG_phrase + " work ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //how does the antilock brake system work ?
                        //how does A0=PAG work? A0=PAG A1=PPT AM-MNR AM-MOD
                        if (A0PAG && AMTMP && !target.equals("be")) {
                            Questions.write("\t" + "rule 40" + "\t" + "When do " + A0PAG_phrase + " " + target + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        if (A1PPT && content.contains("to " + target) && !target.equals("be")) {

                            Questions.write("\t" + "rule 41a" + "\t" + "How do i " + target + (CV_phrase != null ? " " + CV_phrase : "") + " " + (A1PPT_phrase != null ? A1PPT_phrase : "") + " " + (A2PPT_phrase != null ? A2PPT_phrase : "") + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how do i open the sunroof ?
                        if (A1PPT && AMMOD && !target.equals("be")) {

                            Questions.write("\t" + "rule 41a" + "\t" + "How do i " + target + (CV_phrase != null ? " " + CV_phrase : "") + " " + (A1PPT_phrase != null ? A1PPT_phrase : "") + " " + (A2PPT_phrase != null ? A2PPT_phrase : "") + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how do i unlock the passenger door ?
                        if (A1PPT && content.contains("to " + target) && !target.equals("be")) {
                            Questions.write("\t" + "rule 41b" + "\t" + "How can i " + target + (CV_phrase != null ? " " + CV_phrase : "") + " " + (A1PPT_phrase != null ? A1PPT_phrase : "") + " " + (A2PPT_phrase != null ? A2PPT_phrase : "") + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        if (A1PPT && A0PAG && (flag == '*') && !target.equals("be")) {
                            Questions.write("\t" + "rule 41c" + "\t" + "How can i " + target + " " + A1PPT_phrase + (CV_phrase != null ? " " + CV_phrase : "") + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how can i target A1=PPT? how can i restart the liftgate operation in the reverse direction ?
                        if (A1PPT && A0PAG && (flag == '*') && !target.equals("be")) {
                            Questions.write("\t" + "rule 41c2" + "\t" + "How do i " + target + " " + A1PPT_phrase + (CV_phrase != null ? " " + CV_phrase : "") + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        if (AMMNR && AMLOC && content.contains("to " + target) && !target.equals("be")) {
                            Questions.write("\t" + "rule 41d" + "\t" + "How can i " + AMMNR_phrase + " " + target + " " + AMLOC_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //how can i safely drive on hills ?
                        //how can i AM-MNR target AM-LOC ?

                        if (A1PPT && A2PRP && !target.equals("be")) {
                            Questions.write("\t" + "rule 41e" + "\t" + "How can i " + target + " " + A1PPT_phrase + " " + A2PRP_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }


                        //how can i use the accelerator to increase speed while adaptive cruise control is at a set speed ? 
                        //how can i target A1=PPT A2=PRP?
                        if (A2PPT && content.contains("to " + target) && !target.equals("be")) {
                            Questions.write("\t" + "rule 41h" + "\t" + "How do i " + target + " " + A2PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //How do i assist in locating the top tether anchors ?
                        if (A2GOL && content.startsWith("to")) {
                            Questions.write("\t" + "rule 41r" + "\t" + "How do i " + target + " " + A2GOL_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how do i shift into the neutral transfer case setting ?
                        //how do i target A2=GOL?

                        if (AMMNR && AMNEG && !A0PAG && !target.equals("be")) {
                            Questions.write("\t" + "rule 41f" + "\t" + "How can i " + target + " " + AMMNR_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        if (A1PPT && AMMNR && !target.equals("be")) {
                            Questions.write("\t" + "rule 41g" + "\t" + "How do i " + target + " " + A1PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //how do i adjust the ambient lighting ?
                        //how do i target A1=PPT? 

                        if (A1PPT && A0PAG && AMTMP) {
                            Questions.write("\t" + "rule 41q" + "\t" + "How do i " + target + " " + A1PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //how do i remove sunscreen from interior surfaces
                        //how do i target A1=PPT? A0=PAG
                        if (A1PPT && A2EXT && AMMOD) {
                            Questions.write("\t" + "rule 41m" + "\t" + "How do i " + target + " " + A1PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how do i increase the adaptive cruise control gap distance ?
                        //how do i target A1=PPT? A2=EXT AM-MOD
                        if (AMPNC && AMPNC_phrase.startsWith("for")) {
                            String[] arr = AMPNC_phrase.split(" ", 2);
                            if (arr[0].equals("for"))
                                AMPNC_phrase = arr[1];
                            Questions.write("\t" + "rule 41i" + "\t" + "How do i get " + "" + AMPNC_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how do i get more rear window washer cycles ?
                        //how do i get AM-PNC ?
                        if (A1PPT && A2DIR && AMMOD) {

                            Questions.write("\t" + "rule 41j" + "\t" + "How do i get " + "" + A1PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //how do i get replacement remote controls ?
                        //how do i get A1=PPT?
                        if (A1PPT && AMTMP && AMLOC) {

                            Questions.write("\t" + "rule 41n" + "\t" + "How do i know " + "" + AMTMP_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //how do i know when cruise control is turned on ?
                        //how do i know AM-TMP? AM-LOC A1=PPT
                        if (A1PPT && A0CAU && !AMTMP) {

                            Questions.write("\t" + "rule 41p" + "\t" + "How do i know " + "" + A1PPT_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how do i know which cruise control is set ?
                        //how do i know A1=PPT? A0=CAU A1=PPT
                        if (A2PRD && A1PPT && AMTMP && A2PRD_phrase.split(" ").length > 1) {
                            String[] arr = A2PRD_phrase.split(" ", 2);
                            if (arr[0].equals("for"))
                                A2PRD_phrase = arr[1];
                            Questions.write("\t" + "rule 41k" + "\t" + "How do i get " + "" + A2PRD_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how do i get the highest setting for the seat heating feature ? 
                        //how do i get A2=PRD_without_for?
                        if (A1PPT && AMDIR && AMPRP) {
                            String[] arr = AMPRP_phrase.split(" ", 2);
                            if (arr[0].equals("to"))
                                AMPRP_phrase = arr[1];
                            Questions.write("\t" + "rule 41L" + "\t" + "How do i " + "" + AMPRP_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }
                        //how do i idle the vehicle with the automatic headlamp system off ?
                        //how do i AM-PRP_without_to
                        if ((A1PRD || A1PPT) && AMADV && (flag == '*') && !target.equals("be")) {
                            if (A1PRD) {
                                Questions.write("\t" + "rule 42" + "\t" + "How can i " + (AMMNR_phrase != null ? AMMNR_phrase : "") + " " + target + " " + A1PRD_phrase + " ?\t");
                                Questions.write(content + "\t" + content + "\n");
                                no++;
                            } else if (A1PPT) {
                                Questions.write("\t" + "rule 42" + "\t" + "How can i " + (AMMNR_phrase != null ? AMMNR_phrase : "") + " " + target + " " + A1PPT_phrase + " ?\t");
                                Questions.write(content + "\t" + content + "\n");
                                no++;
                            }
                        }
                        //Question: how can i automatically fold the exterior mirrors ? 

                        if (A1PPT && AMGOL && CV && !target.equals("be")) {
                            Questions.write("\t" + "rule 43" + "\t" + "Why  should i " + target + " " + A1PPT_phrase + " " + CV_phrase + " ?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        //Why i should target A1=PPT C-V?
                        /////////////////////////////////////////////////////////////
                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMMOD && !AMNEG && !AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {

                            Questions.write("\t" + "rule 24" + "\t" + "What does " + A0PAG_phrase + " do?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }
                        if (!A2 && !A1PRD && !A2PRD && !A1DIR && !A3DIR && A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && !AMADV && !A2DIR && !AMDIR && !A0PAG_phrase.equalsIgnoreCase("you") && A0PAG_phrase.split(" ").length > 1) {
                            Questions.write("\t" + "rule 32" + "\t" + "What is the " + A0PAG_phrase + "?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }
                        if (A0CAU && A1PPT) {
                            Questions.write("\t" + "rule 30" + "\t" + "What is the " + A0CAU_phrase + "?" + "\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }
                        if (!A2 && A1PPT && A0CAU && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && !AMADV && !AMMOD && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 31" + "\t" + "Why is there the " + A0CAU_phrase + "?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }
                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMMOD && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 13" + "\t" + "What do you " + target + " " + AMTMP_phrase + " " + A2DIR_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                            Questions.write("\t" + "rule 14" + "\t" + "What do you do " + " " + A2DIR_phrase + "?\t");
                            Questions.write(target + " " + A1PPT_phrase + ".\t" + content + "\n");
                            no++;

                            Questions.write("\t" + "rule 15" + "\t" + "How " + A2DIR_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;
                            ///Muath remider to do modifcation

                        }
                        if (!A1 && !A2 && !AMPRP && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 22" + "\t" + AMTMP_phrase + ", what does " + A0PAG_phrase + " do?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }
                        if (!A2 && !A1PPT && A1PAG && !A1PRD && A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMMOD && !AMNEG && !AMTMP && !AMLOC && !AMMNR && !A2DIR && !AMDIR) {

                            Questions.write("\t" + "rule 1" + "\t" + "What to consider when there is " + A1PAG_phrase + " " + A2PRD_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }
                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMMOD && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {

                            //			 Questions.write("\t" + "rule 1" + "\t" + "When is "+A1PPT_phrase+ " "+target+"ed?\t");
                            //			 Questions.write(content+"\t"+content+"\n");
                            //			 no++;
                            //Newly added
                            //What should I do when tread wear indicators first become visible?
                            Questions.write("\t" + "rule 2" + "\t" + "What to consider " + AMTMP_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        //		flash.01		A1=PPT (The ESC Activation / Malfunction Indicator Light ( located in the instrument cluster ))		AM-TMP (as soon as the tires lose traction)	

                        /*[TARGET][A1=GOL][A2=PPT]
								Who do you...[TARGET]...A2=PPT?
								A1=GOL
								Q: Who do you instruct to buckle their seat belts Before starting your vehicle?
								A:all other occupants.

							[TARGET][A1=GOL][A2=PPT]
							What do you[TARGET]A1=GOL?
							A2=PPT
							Q: What do you instruct all other occupants Before starting your vehicle?
							A:to buckle their seat belts.*/
                        if (!A2 && !A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && A1GOL && A2PPT && !A2GOL && !A4GOL && !AMMOD && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && !AMDIR) {
                            Questions.write("\t" + "rule 3" + "\t" + "What do you " + target + " " + A1GOL_phrase + " " + AMTMP_phrase + "?\t");
                            Questions.write(A2PPT_phrase + ".\t" + content + "\n");
                            no++;

                            Questions.write("\t" + "rule 4" + "\t" + "Who do you " + target + " " + A2PPT_phrase + " " + AMTMP_phrase + "?\t");
                            Questions.write(A1GOL_phrase + ".\t" + content + "\n");
                            no++;

                        }

                        /*
							  * [TARGET][A1=PRD][AM-TMP]
								What do you do [AM-TMP]?
								TARGET A1=PRD.

								[TARGET][A1=PRD][AM-TMP]
								When do you...[TARGET]... A1=[PRD]?
								AM-TMP
                         */
                        if (!A2 && !A1PPT && A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMMOD && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && !AMDIR) {
                            Questions.write("\t" + "rule 5" + "\t" + "What should I do " + AMTMP_phrase + "?\t");
                            Questions.write(target + " " + A1PRD_phrase + ".\t" + content + "\n");
                            no++;

                            Questions.write("\t" + "rule 6" + "\t" + "When do you " + target + " " + A1PRD_phrase + "?\t");
                            Questions.write(AMTMP_phrase + ".\t" + content + "\n");
                            no++;

                        }

                        if (!A1 && !A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMMOD && !AMNEG && !AMTMP && AMLOC && !AMADV && !AMMNR && !AMDIR) {
                            Questions.write("\t" + "rule 7" + "\t" + "Where is the " + A1PPT_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        /*[TARGET][A1=DIR][A2=PRD][AM-NEG]
								Do you [TARGET] [A1=DIR] [A2=PRD]?
								AM-NEG*/
                        if (!A2 && A1PPT && !A1PRD && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMMOD && AMNEG && (AMLOC || AMTMP) && !AMADV && !AMMNR && !AMDIR) {
                            Questions.write("\t" + "rule 8" + "\t" + "When not to " + target + " " + A1PPT_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        /*[TARGET][A1=PPT][A2=GOL][AM-MOD]
								What do you TARGET A2=GOL about?
								A1=PPT*/
                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && A2GOL && !A4GOL && AMMOD && !AMNEG && !AMTMP && !AMLOC && !AMADV && !AMMNR && !AMDIR) {
                            Questions.write("\t" + "rule 9" + "\t" + "What do you " + target + " " + A2GOL_phrase + "?\t");
                            Questions.write(A1PPT_phrase + ".\t" + content + "\n");
                            no++;

                        }

                        /*[TARGET][A1=PPT][A2=PRD]
								How should [TARGET] [A1=PPT][AM-TMP]
								[A2=PRD]
								How should be the shift lever/gear selector?
								
                         */
                        if (!A2 && A1PPT && !A1PRD && A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && !AMDIR) {
                            Questions.write("\t" + "rule 10" + "\t" + "How should " + A1PPT_phrase + " " + target + " " + AMTMP_phrase + "?\t");
                            Questions.write(A2PRD_phrase + ".\t" + content + "\n");
                            no++;

                        }

                        /*[TARGET][A1=PPT][A2=PRD]
								Where/How  [TARGET] [A1=PPT]?
								[A2=PRD]
								Where/How is the Remote Start / Keyless Enter - N - Go� Key Fob?
								
                         */
 /*	 if(!A2 && A1PPT && !A1PRD &&A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && !AMADV && !AMMNR && !AMDIR){
								 Questions.write("\t" + "rule 11" + "\t" +"Where/How "+target+" "+A1PPT_phrase+"?\t");
								 Questions.write(A2PRD_phrase+".\t"+content+"\n");
								 no++;

							 }*/
 /*[TARGET][A1=PPT][AM-ADV]
								What to do [AM-ADV]?
								TARGET [A1=PPT]
								What to do	If the vehicle fails to start?
                         */
                        if (!A2 && A1PPT && !A1PRD && A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && !AMADV && !AMMNR && !AMDIR) {
                            Questions.write("\t" + "rule 11" + "\t" + "How do I know if " + A1PPT_phrase + " is " + A2PRD_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && AMADV && !AMDIR) {
                            Questions.write("\t" + "rule 12" + "\t" + "What should I do " + AMADV_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        /*Q1: [TARGET][A1=PPT]
								What do you recommend to [TARGET][A1=PPT]? 
								temp[2](content)
							Question 1: What do you recommend to ensure reliable starting at these temperatures?
Answer 1:To ensure reliable starting at these temperatures, use of an externally powered electric engine block heater (available from your authorized dealer) is recommended..
								*
								*
                         */
                        if (!A2 && !AMPRP && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && A2GOL && !A4GOL && !AMMOD && !AMNEG && !AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 16" + "\t" + "Define " + A2GOL_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        /*Question 1: What do you recommend to ensure reliable starting at these temperatures?
Answer 1:To ensure reliable starting at these temperatures, use of an externally powered electric engine block heater (available from your authorized dealer) is recommended..
								*
								*
                         */
                        if (!A2 && AMPRP && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 17" + "\t" + "Why do you " + target + " " + A1PPT_phrase + "?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }
                        /*
							  * Question 1: Why do you plug The engine block heater in at least one hour?
								Answer 1:to have an adequate warming effect on the engine.*/

                        if (!A2 && AMPRP && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 18" + "\t" + "Why do you " + target + " " + A1PPT_phrase + " " + AMTMP_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        /*
							  * Question : Why is engine power reduced by ESC?
								Answer :To help the vehicle maintain the desired path.*/
                        if (!A2 && AMPRP && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 19" + "\t" + "Why is " + A1PPT_phrase + " " + target + "ed?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }

                        /*
							  * Question 1: Why do you plug The engine block heater in at least one hour?
								Answer 1:to have an adequate warming effect on the engine.*/
                        if (A1 && !A2 && !AMPRP && !A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMTMP && AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 20" + "\t" + "Where is " + A1_phrase + " located?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        /*
							  * Question : What are ABS-inducing conditions?
								Answer :Ice, snow, gravel, bumps, railroad tracks, loose debris, or panic stops.*/
                        if (!A1 && !A2 && !AMPRP && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && A2GOL && !A4GOL && !AMNEG && !AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 21" + "\t" + "What is " + A2GOL_phrase + "?\t");
                            Questions.write(content + ".\t" + content + "\n");
                            no++;

                        }

                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && !AMADV && !AMMNR && !A2DIR && AMDIR) {

                            Questions.write("\t" + "rule 23" + "\t" + "Why does " + A1PPT_phrase + " " + target + " " + AMDIR_phrase + "?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;
                        }

                        if (!A2 && !A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {

                            Questions.write("\t" + "rule 25" + "\t" + "Why do " + A0PAG_phrase + " " + target + "?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                            Questions.write("\t" + "rule 25" + "\t" + "In what circumstances " + A0PAG_phrase + " " + target + "?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }
                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && AMMOD && !AMNEG && AMTMP && !AMLOC && !AMADV && !AMMNR && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 26" + "\t" + "When does " + A1PPT_phrase + " " + target + "?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }

                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && AMADV && AMMOD && !A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 27" + "\t" + "What " + AMMOD_phrase + " happen " + AMADV_phrase + "?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }

                        if (!A2 && A1PPT && !A1PRD && !A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && AMADV && AMMOD && A2DIR && !AMDIR) {
                            Questions.write("\t" + "rule 28" + "\t" + "How " + AMMOD_phrase + " " + A1PPT_phrase + " be " + target + "ed?\t");
                            Questions.write(content + "\t" + content + "\n");
                            no++;

                        }
                        //else{
                        //   notCovered++;
                        //}
                        //	 if(!A2 && !A1PPT && !A1PRD &&!A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && A2GOL && !A4GOL && !AMNEG && !AMLOC && !AMADV && !AMMOD && !A2DIR  && !AMDIR){
                        //		 Questions.write("\t" + "rule 29" + "\t" +"How to "+ target + " "+ A2GOL_phrase+ "?\t");
                        //		 Questions.write(content+"\t"+content+"\n");
                        //		 no++;

                        //	 }
                        //	 if(!A2 && A0CAU && A1PPT && !A1PRD &&!A2PRD && !A1DIR && !A3DIR && !A0PAG && !A1GOL && !A2PPT && !A2GOL && !A4GOL && !AMNEG && !AMLOC && !AMADV && !AMMOD && !A2DIR  && !AMDIR){
                        /**
                         * ***************************************************************************************
                         */


                    }


                } catch (Exception a) {
                    System.out.println(a);
                }

                inputLine = inputFile.readLine();

            }
            System.out.println("track is: " + track);
            inputFile.close();

            Questions.close();
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }
    }

}