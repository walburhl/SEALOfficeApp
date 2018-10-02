package com.olsencheung4930.studentorgtimetablev1;

public class Organization {



    private String[] STUDENT_ORGANIZATION = new String[] {
            "Administration",
            "Paws for a Cause",
            "AAA - Asian American Association",
            "African Students Union",
            "UNIDOS",
            "American Institute of Aeronautics and Astronautics (AIAA)",
            "Amicus Curiae",
            "Apiculture Society",
            "Arnold Air Society",
            "Best Buddies",
            "Black Women Empowered",
            "BSAA",
            "Chinese American Cultural Association",
            "China America Business Organization",
            "Chinese Students and Scholars Association",
            "CIA - Chinese Information Association",
            "Circle K",
            "College Mentors for Kids",
            "College Republicans",
            "Colleges Against Cancer",
            "Dance Corps",
            "Delta Epsilon Mu",
            "Delta Sigma Pi",
            "Department of Magical Appreciation",
            "Engineering & Computing Student Council",
            "Engineers without Borders",
            "eSports",
            "FWORD",
            "Graduate Students of Color",
            "Green Oxford",
            "Habitat for Humanity",
            "InFocus",
            "International Student Advisory Council",
            "International Student Organization",
            "Japanese Culture & Language Club",
            "Kappa Phi Club",
            "Korean American Student Association",
            "League of Geeks",
            "MED Life",
            "MEGA",
            "MSEA",
            "Miami Independents",
            "Miami MED",
            "Miami Medieval",
            "Miami Quarterly",
            "Miami Students for Israel",
            "Miami University Fashion Design",
            "Model UN",
            "MU Anime",
            "MU Dance Marathon",
            "MUndead",
            "NRHH",
            "OIP-u",
            "Phi Sigma Pi",
            "RHA",
            "Secular Students",
            "Sketched Out",
            "Swing Syndicate",
            "Soul2Soul A Capella",
            "Spectrum",
            "Speech Language (NSSLHA)",
            "Stage Left",
            "Strategy Gaming Club",
            "Students for Life",
            "Theta Tau",
            "TINT",
            "Treblemakers",
            "Young Democrats",
    };


    public String[] getStudentOrganizationList(){
        return STUDENT_ORGANIZATION;
    }

    public Boolean search(String target){
        for(int i = 0; i<STUDENT_ORGANIZATION.length; i++){
            if(STUDENT_ORGANIZATION[i].equalsIgnoreCase(target))
                return true;
        }

        return false;
    }



}
