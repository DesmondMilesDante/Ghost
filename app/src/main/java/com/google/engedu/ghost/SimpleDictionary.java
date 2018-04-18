/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix==""){
            Random r=new Random();
            int i=r.nextInt(words.size());
            return words.get(i);
        }
        else{
            int f=0,l=words.size()-1,mid;
            while(f<=l){
                mid=(f+l)/2;
                String s=words.get(mid);
                int j=prefix.compareTo(s);
                if(s.length()>=prefix.length()&&s.startsWith(prefix)){
                    return  s;
                }
                else if(j<0){
                    l=mid-1;
                }
                else {
                    f=mid+1;
                }
            }
        }
        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        int f = 0, l = words.size() - 1, mid= -1;;
        while (f <= l) {
            mid = (f + l) / 2;
            String s = words.get(mid);
            int j = prefix.compareTo(s);
            if (s.length() >= prefix.length() && s.startsWith(prefix)) {

                break;
            } else if (j < 0) {
                l = mid - 1;
            } else {
                f = mid + 1;
            }
        }
        if (f > l) {
            return null;
        } else {
            ArrayList<String> odd = new ArrayList<>();
            ArrayList<String> even = new ArrayList<>();

            int i = mid;
            String s = words.get(i);

            while (s.length() >= prefix.length() && s.startsWith(prefix)) {
                if (s.length() % 2 == 0) {
                    even.add(s);
                } else {
                    odd.add(s);
                }
                i--;
                if (i < 0)
                    break;
                s = words.get(i);
            }
            i = mid + 1;
            while (s.length() >= prefix.length() && s.startsWith(prefix)) {
                if (s.length() % 2 == 0) {
                    even.add(s);
                } else {
                    odd.add(s);
                }
                i++;
                if (i >= words.size())
                    break;
                s = words.get(i);
            }
            Random r = new Random();
            if (prefix.length() % 2 == 0) {
                if (even.isEmpty()) {
                    int n = r.nextInt(odd.size());
                    return odd.get(n);

                } else {
                    int n = r.nextInt(even.size());
                    return even.get(n);
                }
            } else {
                if (odd.isEmpty()) {
                    int n = r.nextInt(even.size());
                    return even.get(n);

                } else {
                    int n = r.nextInt(odd.size());
                    return odd.get(n);
                }
            }

        }
    }
}

