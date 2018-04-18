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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        HashMap<String, TrieNode> children = this.children;
        for (int i = 0; i < s.length(); i++) {
            TrieNode temp = this;
            if (children.containsKey(s.substring(0, i + 1))) {
                temp = children.get(s.substring(0, i + 1));
                children = temp.children;
            } else {
                temp = new TrieNode();
                children.put(s.substring(0, i + 1), temp);
            }
            children = temp.children;
            if (i == s.length() - 1)
                temp.isWord = true;

        }
    }

    public boolean isWord(String s) {
        HashMap<String, TrieNode> children = this.children;
        TrieNode temp = this;
        for (int i = 0; i < s.length(); i++) {
            if (children.containsKey(s.substring(0, i + 1))) {
                temp = children.get(s.substring(0, i + 1));
                children = temp.children;
            } else {
                return false;
            }
        }
        if (temp.isWord) {
            return true;
        } else {
            return false;
        }
    }

    public String getAnyWordStartingWith(String s) {
        HashMap<String, TrieNode> children = this.children;
        TrieNode temp = this;
        for (int i = 0; i < s.length(); i++) {
            if (children.containsKey(s.substring(0, i + 1))) {
                temp = children.get(s.substring(0, i + 1));
                children = temp.children;
            } else {
                return null;
            }

        }
        if (temp.isWord)
            return s;
        String key = s;
        while (!temp.isWord) {
            Set<String> keyset = temp.children.keySet();
            String keys[] = keyset.toArray(new String[0]);
            Random r = new Random();
            int n = r.nextInt(keys.length);
            key = keys[n];
            temp = children.get(key);
            children = temp.children;

        }
        return key;
    }

    public String getGoodWordStartingWith(String s) {
        HashMap<String, TrieNode> children = this.children;
        TrieNode temp = this;
        for (int i = 0; i < s.length(); i++) {
            if (children.containsKey(s.substring(0, i + 1))) {
                temp = children.get(s.substring(0, i + 1));
                children = temp.children;
            } else {
                return null;
            }

        }
        Set<String> keyset = temp.children.keySet();
        String keys[] = keyset.toArray(new String[0]);
        ArrayList<String> notWords = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            if (children.get(keys[i]).isWord) {

            } else {
                notWords.add(keys[i]);
            }
            if (notWords.isEmpty()) {
                Random r = new Random();
                int n = r.nextInt(keys.length);
                return keys[n];
            } else {
                Random r = new Random();
                int n = r.nextInt(notWords.size());
                return keys[n];
            }
        }
       return null;
    }
}

