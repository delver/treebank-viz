Treebank Visualization
======================

SVG graph generation of treebank language parsing.

Start with:

    $ lein ring server

SVG graph available at 
[http://localhost:3000/svg?q=Find the two week period in the next six months with the most European conferences?] 
(http://localhost:3000/svg?q=Find the two week period in the next six months with the most European conferences?)* 
yields:

![SVG](https://rawgithub.com/delver/treebank-viz/master/example.svg)

for example, or similar text representation at 
[http://localhost:3000/text?q=Find the two week period in the next six months with the most European conferences?] 
(http://localhost:3000/text?q=Find the two week period in the next six months with the most European conferences?)
produces a clojure-readable data structure as follows:

```clojure
{:desc "Unknown",
 :id :G__5472,
 :tag TOP,
 :chunk
 ({:desc "Verb Phrase. ",
   :id :G__5473,
   :tag VP,
   :chunk
   ({:desc "Verb, base form", :id :G__5474, :tag VB, :chunk ("Find")}
    {:desc "Noun Phrase. ",
     :id :G__5475,
     :tag NP,
     :chunk
     ({:desc "Noun Phrase. ",
       :id :G__5476,
       :tag NP,
       :chunk
       ({:desc "Noun Phrase. ",
         :id :G__5477,
         :tag NP,
         :chunk
         ({:desc "Determiner", :id :G__5478, :tag DT, :chunk ("the")}
          {:desc "Cardinal number",
           :id :G__5479,
           :tag CD,
           :chunk ("two")}
          {:desc "Noun, singular or mass",
           :id :G__5480,
           :tag NN,
           :chunk ("week")}
          {:desc "Noun, singular or mass",
           :id :G__5481,
           :tag NN,
           :chunk ("period")})}
        {:desc "Prepositional Phrase.",
         :id :G__5482,
         :tag PP,
         :chunk
         ({:desc "Preposition or subordinating conjunction",
           :id :G__5483,
           :tag IN,
           :chunk ("in")}
          {:desc "Noun Phrase. ",
           :id :G__5484,
           :tag NP,
           :chunk
           ({:desc "Determiner", :id :G__5485, :tag DT, :chunk ("the")}
            {:desc "Adjective", :id :G__5486, :tag JJ, :chunk ("next")}
            {:desc "Cardinal number",
             :id :G__5487,
             :tag CD,
             :chunk ("six")}
            {:desc "Noun, plural",
             :id :G__5488,
             :tag NNS,
             :chunk ("months")})})})}
      {:desc "Prepositional Phrase.",
       :id :G__5489,
       :tag PP,
       :chunk
       ({:desc "Preposition or subordinating conjunction",
         :id :G__5490,
         :tag IN,
         :chunk ("with")}
        {:desc "Noun Phrase. ",
         :id :G__5491,
         :tag NP,
         :chunk
         ({:desc "Determiner", :id :G__5492, :tag DT, :chunk ("the")}
          {:desc "Adjective Phrase.",
           :id :G__5493,
           :tag ADJP,
           :chunk
           ({:desc "Adverb, superlative",
             :id :G__5494,
             :tag RBS,
             :chunk ("most")}
            {:desc "Adjective",
             :id :G__5495,
             :tag JJ,
             :chunk ("European")})}
          {:desc "Noun, singular or mass",
           :id :G__5496,
           :tag NN,
           :chunk ("conferences?")})})})})})}
```

* - Question attribution: [@garybernhardt](https://twitter.com/garybernhardt/status/376145933827727360)

License
-------
As per [Creative Commons 3.0](http://creativecommons.org/licenses/by/3.0/legalcode)
